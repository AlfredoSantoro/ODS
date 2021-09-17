package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.model.AccessAuthorizations
import com.unisa.sesalab.ods.model.OpeningTime
import com.unisa.sesalab.ods.model.Reservation
import org.springframework.stereotype.Service

@Service
class SESALabReservationRulesServiceImpl: ReservationRulesService
{
    @Throws(ReservationConstraintsException::class)
    override fun validReservation(reservation: Reservation,
                                  userAuthorizationsList: List<AccessAuthorizations>,
                                  seSaLabOpeningTimes: List<OpeningTime>)
    {
        // Sorting ascending of user authorizations
        val userAuthorizationsSorted = userAuthorizationsList.sortedBy { it.end }
        if ( userAuthorizationsSorted.isNotEmpty() )
        {
            /*
            * Check that the interval of the reservation is included interval in the time of the latest granted authorization
            * to access the lab.
             */
            val latestAuthorization = userAuthorizationsSorted.last()
            if ( latestAuthorization.granted )
            {
                /*
                * Check that the reservation interval is included in the opening times of the laboratory.
                * If the reservation interval is not included in the laboratory opening times then throws an exception
                 */
                val openingTime = seSaLabOpeningTimes.find { it.dayOfWeek === reservation.start.dayOfWeek }
                if ( !this.reservationIsIncludedInTheOpeningTimesOfTheLab(reservation, openingTime) )
                {
                    throw ReservationConstraintsException("Cannot create a reservation beyond the lab opening times")
                }
            }
            else
            {
                throw ReservationConstraintsException("reservation not allowed because the authorization #${latestAuthorization.id} has not been granted")
            }
        }
        else
        {
            throw ReservationConstraintsException("reservation not allowed because user does not have authorization to access the lab")
        }
    }

    private fun reservationIsIncludedInTheOpeningTimesOfTheLab(reservation: Reservation, openingTime: OpeningTime?): Boolean
    {
        return if ( openingTime === null ) false
        else
        {
            reservation.start.toOffsetTime() >= openingTime.open
                    && reservation.start.toOffsetTime() <= openingTime.close
                    && reservation.end.toOffsetTime() <= openingTime.close
        }
    }
}