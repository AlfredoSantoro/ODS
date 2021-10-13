package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.model.Reservation
import development.kit.reservation.ReservationRules

interface ReservationRulesService: ReservationRules
{
    @Throws(ReservationConstraintsException::class)
    fun checkNewReservation(reservation: Reservation)

    @Throws(ReservationConstraintsException::class)
    fun checkUpdateReservation(reservation: Reservation)
}