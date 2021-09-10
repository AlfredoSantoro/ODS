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

    override fun createReservation(reservation: IReservations)
    {
        /* PSEUDO CODE
        * Check that the reservation interval is a time which the laboratory is open
        * If the reservation interval is not included in the laboratory opening times then throws an exception
        * Check that the seat is available for the selected date and time
        * If the seat is occupied or unavailable then throws an exception
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