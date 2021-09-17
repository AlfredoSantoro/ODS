package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.model.AccessAuthorizations
import com.unisa.sesalab.ods.model.OpeningTime
import com.unisa.sesalab.ods.model.Reservation

interface ReservationRulesService
{
    @Throws(ReservationConstraintsException::class)
    fun validReservation(reservation: Reservation, userAuthorizationsList: List<AccessAuthorizations>,
                         seSaLabOpeningTimes: List<OpeningTime>)
}