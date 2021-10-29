package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.dto.CheckInDTO
import com.unisa.sesalab.ods.factory.ReservationEntityFactory
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.service.reservation.ReservationRulesService
import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.tagnfc.TagNFCService
import development.kit.identifier.TagNFC
import development.kit.rules.ReservationRuleManager
import org.springframework.stereotype.Service

@Service
class CheckInRuleServiceImpl(
    private val reservationService: ReservationService,
    private val reservationRulesService: ReservationRulesService,
    private val tagNFCService: TagNFCService
): CheckInRuleService
{
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
}