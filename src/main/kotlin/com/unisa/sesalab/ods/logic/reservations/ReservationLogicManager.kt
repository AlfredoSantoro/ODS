package com.unisa.sesalab.ods.logic.reservations

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import com.unisa.sesalab.ods.logic.BasicLogicManager
import com.unisa.sesalab.ods.model.*
import com.unisa.sesalab.ods.repository.BaseCrudRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.OffsetDateTime

class ReservationLogicManager<K>(
        private val reservationRepository: BaseCrudRepository<IReservations, K>
):BasicLogicManager<IReservations, K>(reservationRepository), ReservationOperations<IReservations>
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationLogicManager::class.java)

    @Synchronized
    override fun createReservation(reservation: IReservations)
    {
        /* PSEUDO CODE
        * Sorting ascending of user authorizations
        * Check that the interval of the reservation is included interval in the time of the latest granted authorization
        * to access the lab.
        * If the user is not authorized then, throws exception.
        * Check that the reservation interval is included in the opening times of the laboratory.
        * If the reservation interval is not included in the laboratory opening times then throws an exception
        * Check that the seat to reserve is available
        * If the seat is occupied or unavailable then throws an exception
        * Check that the user does not already have a reservation on going
        * if all the previous rules are respected, then create a reservation for the user
        * */
    }

    override fun updateReservation(reservation: IReservations)
    {

    }

    override fun deleteReservation(reservation: IReservations)
    {

    }

    override fun suspendReservation(reservation: IReservations)
    {

    }

    override fun viewReservationDetails(reservationId: Long): IReservations
    {
        return Reservations("", OffsetDateTime.now(),
                OffsetDateTime.now(),
                Users(UserDTO("", "", UserType.USER, "", "", "")),
                Seats("", true, TagNfc("", "") ))
    }

    override fun reservationHistory(until: OffsetDateTime): Set<IReservations>
    {
        return setOf()
    }


}