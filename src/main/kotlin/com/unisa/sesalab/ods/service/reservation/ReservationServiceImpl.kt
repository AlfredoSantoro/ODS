package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.ReservationUpdateDTO
import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.exception.SettingException
import com.unisa.sesalab.ods.factory.AccountFactory
import com.unisa.sesalab.ods.factory.AssetFactory
import com.unisa.sesalab.ods.factory.ReservationEntityFactory
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import com.unisa.sesalab.ods.service.checkin.CheckInService
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.setting.SettingService
import development.kit.reservation.ReservationManager
import development.kit.utils.OffsetDateTimeUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.OffsetDateTime

@Service
class ReservationServiceImpl(
        private val reservationRepository: ReservationRepository,
        private val seatService: SeatService,
        private val settingService: SettingService,
        private val reservationRulesService: ReservationRulesService,
        private val checkInService: CheckInService
): ReservationService
{
    @Value("\${settings.default-names.reservationHistory}")
    private lateinit var reservationHistorySetting: String

    @Value("\${settings.default-names.reservationDurationHour}")
    private lateinit var reservationDurationHour: String

    private val logger: Logger = LoggerFactory.getLogger(ReservationServiceImpl::class.java)

    private val reservationManager = ReservationManager(this.reservationRulesService)

    override fun createReservation(reservationInsertDTO: ReservationInsertDTO)
    {
        synchronized(Any()) {
            val newReservation = this.reservationManager.createReservation(
                AccountFactory.createAccount(reservationInsertDTO.account), reservationInsertDTO.start,
                reservationInsertDTO.reservationDuration, -1, AssetFactory.createAsset(reservationInsertDTO.studySeat))

            val reservationToSave = ReservationEntityFactory.createReservation(newReservation, reservationInsertDTO.account,
                reservationInsertDTO.studySeat, reservationInsertDTO.name)

            this.reservationRepository.insertReservation(reservationToSave)

            this.logger.info("### asset #${reservationInsertDTO.studySeat.id} reserved " +
                    "by user ${reservationInsertDTO.account.id} from ${reservationToSave.start} to ${reservationToSave.end}")
        }
    }

    override fun updateReservation(reservationUpdateDTO: ReservationUpdateDTO)
    {
        val assetToReserve = this.seatService.findById(reservationUpdateDTO.seatId)
        val reservationToUpdate = this.reservationRepository.viewReservation(reservationUpdateDTO.reservationId)
        if ( assetToReserve !== null && reservationToUpdate !== null )
        {
            val reservationDurationHourSetting = this.settingService.findByName(this.reservationDurationHour)
                    ?: throw SettingException("### reservationDurationHourSetting not found")

            val reservationEnd =
                    reservationUpdateDTO.start.plus(reservationDurationHourSetting.value.toLong(),
                            reservationDurationHourSetting.representationUnit)

            reservationToUpdate.name = reservationUpdateDTO.name
            reservationToUpdate.start = reservationUpdateDTO.start
            reservationToUpdate.end = reservationEnd
            reservationToUpdate.studySeatReserved = assetToReserve
            this.reservationRulesService.checkUpdateReservation(reservationToUpdate)
            this.reservationRepository.updateReservation(reservationToUpdate)
        }
        else
        {
            throw ReservationConstraintsException("Cannot update a reservation with the asset or id null")
        }
    }

    override fun deleteReservation(id: Long)
    {
        val res = this.reservationRepository.viewReservation(id)
        if ( res == null ) { throw ReservationConstraintsException("Cannot delete reservation #$id because does not exist")}
        else
        {
            this.reservationRepository.deleteReservationById(id)
        }
    }

    override fun terminateAllReservationsByIds(ids: List<Long>): Int { return this.reservationRepository.terminateAllReservationsById(ids) }

    override fun pauseReservation(id: Long)
    {
        val res = this.reservationRepository.viewReservation(id)
        if ( res !== null ) { res.inPause = true }
        else { throw ReservationConstraintsException("Cannot pause reservation #$id because does not exist") }
    }

    override fun findReservationById(id: Long): Reservation? { return this.reservationRepository.viewReservation(id) }

    override fun reservationsHistory(username: String): List<Reservation>
    {
        val reservationHistorySetting = this.settingService.findByName(this.reservationHistorySetting)
        if ( reservationHistorySetting !== null )
        {
            return this.reservationRepository.viewRecentUserReservations(
                    Duration.of(reservationHistorySetting.value.toLong(), reservationHistorySetting.representationUnit),
                username)
        }
        else
        {
            throw SettingException("### reservationHistorySetting not found")
        }
    }

    override fun findReservationOnGoingByUser(username: String): Reservation?
    {
        return this.reservationRepository.findReservationOnGoingByUser(username)
    }

    override fun viewAllReservationsOnGoing(): List<Reservation> { return this.reservationRepository.viewAllReservationsOnGoing() }

    override fun getReservationsToTerminate(onGoingReservations: List<Reservation>, checkInFrequency: Int): List<Long>
    {
        val reservationsIds = arrayListOf<Long>()
        // 1. Process all ongoing reservations
        onGoingReservations.forEach { ongoingReservation ->

            // 2. Find recent check in of reservation
            val lastCheckIn = this.checkInService.findRecentCheckInOfReservation(ongoingReservation.id!!)

            // 3. Checks if ongoing reservations is to delete
            val toTerminate = if ( lastCheckIn == null )
            {
                val endCheckInTimeAllowed = OffsetDateTimeUtils.addDurationToTime(ongoingReservation.start,
                    Duration.ofMinutes(checkInFrequency.toLong()))
                OffsetDateTimeUtils.isStartGreaterThanEnd(OffsetDateTime.now(), endCheckInTimeAllowed)
            }
            else
            {
                val endCheckInTimeAllowed = OffsetDateTimeUtils.addDurationToTime(lastCheckIn.time,
                    Duration.ofMinutes(checkInFrequency.toLong()))
                !lastCheckIn.isValid || OffsetDateTimeUtils.isStartGreaterThanEnd(OffsetDateTime.now(), endCheckInTimeAllowed)
            }
            // 4. If the ongoing reservation is to terminate add it to the reservationsToDeleteIds list
            if ( toTerminate ) reservationsIds.add(ongoingReservation.id)
        }
        return reservationsIds
    }
}