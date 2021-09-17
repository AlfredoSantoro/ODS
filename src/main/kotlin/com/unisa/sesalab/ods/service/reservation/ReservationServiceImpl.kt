package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.ReservationUpdateDTO
import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import com.unisa.sesalab.ods.service.AssetService
import com.unisa.sesalab.ods.service.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class ReservationServiceImpl(
        private val reservationRepository: ReservationRepository,
        private val assetService: AssetService,
        private val userService: UserService,
        private val reservationRulesService: ReservationRulesService
): ReservationService
{
    private val reservationHistoryInDays = 10
    private val logger: Logger = LoggerFactory.getLogger(ReservationServiceImpl::class.java)

    override fun createReservation(reservationInsertDTO: ReservationInsertDTO)
    {
        val assetToReserve = this.assetService.findAssetById(reservationInsertDTO.assetId)
        val user = this.userService.viewAccount(reservationInsertDTO.userId)
        if ( assetToReserve !== null && user !== null )
        {
            // find all reservations overlaps
            this.reservationRulesService.checkReservationOverlaps(user.id!!, reservationInsertDTO.start, reservationInsertDTO.end)

            synchronized(Any()) {
                // Check that the asset to reserve is available
                this.reservationRulesService.checkAssetAvailability(assetToReserve.id!!, reservationInsertDTO.start, reservationInsertDTO.end)
                // validReservation
                val reservationToSave = Reservation(reservationInsertDTO.name, reservationInsertDTO.start,
                        reservationInsertDTO.end, user, assetToReserve)
                this.reservationRulesService.checkNewReservation(reservationToSave)
                this.logger.info("### saving a new reservation for asset #${reservationInsertDTO.assetId} " +
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
        val assetToReserve = this.assetService.findAssetById(reservationUpdateDTO.assetId)
        val reservationToUpdate = this.reservationRepository.viewReservation(reservationUpdateDTO.reservationId)
        if ( assetToReserve !== null && reservationToUpdate !== null )
        {
            if ( this.isOnGoing(reservationToUpdate.start, reservationToUpdate.end) )
            {
                throw ReservationConstraintsException("Cannot update a reservation ongoing")
            }
            // find all reservations overlaps
            this.reservationRulesService.checkReservationOverlaps(assetToReserve.id!!, reservationUpdateDTO.start, reservationUpdateDTO.end)
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

    private fun isOnGoing(start: OffsetDateTime, end: OffsetDateTime): Boolean
    {
        val now = OffsetDateTime.now()
        return (now >= start && now < end)
    }

    override fun pauseReservation(id: Long)
    {
        val res = this.reservationRepository.viewReservation(id)
        if ( res !== null ) { res.inPause = true }
        else { throw ReservationConstraintsException("Cannot pause reservation #$id because does not exist") }
    }

    override fun viewReservationDetail(id: Long): Reservation? { return this.reservationRepository.viewReservation(id) }

    override fun reservationsHistory(): List<Reservation> { return this.reservationRepository.viewRecentReservations(this.reservationHistoryInDays) }

    override fun findAllReservationsOnGoing(): List<Reservation> { return this.reservationRepository.viewReservationsOnGoing() }
}