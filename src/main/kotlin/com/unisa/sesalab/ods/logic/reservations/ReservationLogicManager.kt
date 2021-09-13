/*
package com.unisa.sesalab.ods.logic.reservations

import com.unisa.sesalab.ods.dto.ReservationDTO
import com.unisa.sesalab.ods.enum.IdType
import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.logic.BasicLogicManager
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ReservationLogicManager(
        private val reservationRepository: ReservationRepository<Reservation, ReservationDTO>,
        private val reservationRules: ReservationRules
):BasicLogicManager<Reservation, ReservationDTO>(reservationRepository)
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationLogicManager::class.java)

    fun createNewReservation(reservation: Reservation): Long
    {
        // find all reservations overlaps
        if ( this.reservationRepository.findAllReservationsOverlapsBy(IdType.USER_ID, reservation.user.id, reservation.start, reservation.end).isNotEmpty() )
        {
            throw ReservationConstraintsException("user #${reservation.user.id} cannot have two or more reservations at the same time")
        }
        else
        {
            synchronized(Any()) {
                // Check that the asset to reserve is available
                if ( this.reservationRepository.findAllReservationsOverlapsBy(IdType.ASSET_ID, reservation.asset.id, reservation.start, reservation.end).isNotEmpty() )
                {
                    throw ReservationConstraintsException("asset #${reservation.asset.id} is reserved")
                }
                else
                {
                    // validReservation
                    this.reservationRules.validReservation()
                    this.logger.info("### saving a new reservation for asset #${reservation.asset.id} and user #${reservation.user.id}")
                    val resId = this.save(reservation)
                    this.logger.info("### reservation #$resId saved successfully")
                    return resId
                }
            }
        }
    }
}*/
