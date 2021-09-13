package com.unisa.sesalab.ods.logic.reservations

import com.unisa.sesalab.ods.exception.ReservationConstraintsException

interface ReservationRules
{
    @Throws(ReservationConstraintsException::class)
    fun validReservation()
}