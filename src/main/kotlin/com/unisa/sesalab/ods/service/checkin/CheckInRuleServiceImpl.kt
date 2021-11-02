package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.dto.CheckInDTO
import com.unisa.sesalab.ods.factory.ReservationEntityFactory
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.service.reservation.ReservationRulesService
import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.setting.SettingService
import com.unisa.sesalab.ods.service.tagnfc.TagNFCService
import development.kit.checkin.CheckIn
import development.kit.identifier.TagNFC
import development.kit.rules.ReservationRuleManager
import development.kit.utils.OffsetDateTimeUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class CheckInRuleServiceImpl(
    private val reservationService: ReservationService,
    private val reservationRulesService: ReservationRulesService,
    private val tagNFCService: TagNFCService,
    private val checkInService: CheckInService,
    private val settingService: SettingService
): CheckInRuleService
{
    private val defaultCheckInFrequency = 15

    @Value("\${settings.default-names.checkInFrequency}")
    private lateinit var checkInFrequencySetting: String

    private val reservationRulesManager = ReservationRuleManager(this.reservationRulesService)

    override fun checkNewCheckIn(checkInDTO: CheckInDTO, userLogged: String): Reservation
    {
        val reservation = this.reservationService.findReservationById(checkInDTO.reservationId)
        val baseReservation = if ( reservation !== null) ReservationEntityFactory.createBaseReservation(reservation) else null
        this.reservationRulesManager.checkReservationPresence(baseReservation, checkInDTO.reservationId)

        this.reservationRulesManager.checkReservationOwner(userLogged, baseReservation!!)

        val tagNFC = this.tagNFCService.findTagNFCByValue(checkInDTO.nfcTagId)
        if ( tagNFC === null )
        {
            throw IllegalArgumentException("NFC tag not found")
        }
        this.reservationRulesManager.checkReservationNfcTag(tagNFC.value, TagNFC(tagNFC.name, tagNFC.value))

        this.reservationRulesManager.checkReservationOngoing(baseReservation)

        return reservation!!
    }

    override fun isInTime(checkIn: CheckIn): Boolean
    {
        val frequencyInMinutes = this.settingService.findByName(this.checkInFrequencySetting)?.value ?: this.defaultCheckInFrequency

        val recentCheckIn = this.checkInService.findRecentCheckInOfReservation(checkIn.reservation.id)
        return if ( recentCheckIn == null )
        {
            val endCheckInTimeAllowed = OffsetDateTimeUtils.addDurationToTime(checkIn.reservation.start, Duration.ofMinutes(frequencyInMinutes.toLong()))
            OffsetDateTimeUtils.isStartGreaterThanEnd(endCheckInTimeAllowed, checkIn.time)
        }
        else
        {
            val endCheckInTimeAllowed = OffsetDateTimeUtils.addDurationToTime(recentCheckIn.time, Duration.ofMinutes(frequencyInMinutes.toLong()))
            OffsetDateTimeUtils.isStartGreaterThanEnd(endCheckInTimeAllowed, checkIn.time)
            && recentCheckIn.isValid
        }
    }
}