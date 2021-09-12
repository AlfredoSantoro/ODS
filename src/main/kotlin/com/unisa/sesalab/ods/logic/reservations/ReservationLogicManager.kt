package com.unisa.sesalab.ods.logic.reservations

import com.unisa.sesalab.ods.logic.BasicLogicManager
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ReservationLogicManager<Entity, K>(
        private val reservationRepository: ReservationRepository<Entity, K>,
        private val reservationLogic: ReservationLogic<Entity>
):BasicLogicManager<Entity, K>(reservationRepository)
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationLogicManager::class.java)

    fun createReservation(userId: Long, assetId: Long, reservation: Entity)
    {
        /*
         * find all reservation by userID and check that there is not an existing one for the same user
         * check that does not exist a reservation ongoing for the same asset to reserve
         * after all this, reservationLogic.validReservation(reservation)
         * so, reservationRepository.save(reservation)
         */
    }
}

/* PSEUDO CODE
@Synchronized
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