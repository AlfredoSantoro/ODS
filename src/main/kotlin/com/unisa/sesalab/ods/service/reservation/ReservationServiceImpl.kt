package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.ReservationUpdateDTO
import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.exception.SettingException
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.setting.SettingService
import com.unisa.sesalab.ods.service.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class ReservationServiceImpl(
        private val reservationRepository: ReservationRepository,
        private val seatService: SeatService,
        private val userService: UserService,
        private val settingService: SettingService,
        private val reservationRulesService: ReservationRulesService
): ReservationService
{
    @Value("\${settings.default-names.reservationHistory}")
    private lateinit var reservationHistorySetting: String

    @Value("\${settings.default-names.reservationDurationHour}")
    private lateinit var reservationDurationHour: String

    private val logger: Logger = LoggerFactory.getLogger(ReservationServiceImpl::class.java)

    override fun createReservation(reservationInsertDTO: ReservationInsertDTO)
    {
        /*
            1) controlla l'esistenza del posto che deve essere prenotato e dell'utente
            2) ottiene la durata della prenotazione (parametro)
            3) controlla se ci sono prenotazioni dell'utente che si sovrappongono in quella fascia oraria
            4) controlla se l'asset è disponibile
            5) Check Autorizzazione
            6) Salva
         */
        val assetToReserve = this.seatService.findById(reservationInsertDTO.seatId)
        val user = this.userService.viewAccount(reservationInsertDTO.userId)
        if ( assetToReserve !== null && user !== null )
        {
            val reservationDurationHourSetting = this.settingService.findByName(this.reservationDurationHour)
                    ?: throw SettingException("### reservationDurationHourSetting not found")

            val reservationEnd =
                    reservationInsertDTO.start.plus(reservationDurationHourSetting.value.toLong(),
                            reservationDurationHourSetting.representationUnit)

            // find all reservations overlaps
            this.reservationRulesService.checkReservationOverlaps(user.id!!, reservationInsertDTO.start, reservationEnd)

            synchronized(Any()) {
                // Check that the asset to reserve is available
                this.reservationRulesService.checkAssetAvailability(assetToReserve.id!!, reservationInsertDTO.start, reservationEnd)
                // validReservation
                val reservationToSave = Reservation(reservationInsertDTO.name, reservationInsertDTO.start,
                        reservationEnd, user, assetToReserve)
                this.reservationRulesService.checkNewReservation(reservationToSave)
                this.logger.info("### saving a new reservation for asset #${reservationInsertDTO.seatId} " +
                        "and user #${reservationInsertDTO.userId}")
                this.reservationRepository.insertReservation(reservationToSave)
                this.logger.info("### asset #${assetToReserve.id} reserved by user ${user.id}")
            }
        }
        else
        {
            throw ReservationConstraintsException("Cannot create a reservation with the user or asset null")
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
            reservationToUpdate.seatReserved = assetToReserve
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

    override fun pauseReservation(id: Long)
    {
        val res = this.reservationRepository.viewReservation(id)
        if ( res !== null ) { res.inPause = true }
        else { throw ReservationConstraintsException("Cannot pause reservation #$id because does not exist") }
    }

    override fun viewReservationDetail(id: Long): Reservation? { return this.reservationRepository.viewReservation(id) }

    override fun reservationsHistory(): List<Reservation>
    {
        val reservationHistorySetting = this.settingService.findByName(this.reservationHistorySetting)
        if ( reservationHistorySetting !== null )
        {
            return this.reservationRepository.viewRecentReservations(
                    Duration.of(reservationHistorySetting.value.toLong(), reservationHistorySetting.representationUnit))
        }
        else
        {
            throw SettingException("### reservationHistorySetting not found")
        }
    }

    override fun findAllReservationsOnGoing(): List<Reservation> { return this.reservationRepository.viewReservationsOnGoing() }
}