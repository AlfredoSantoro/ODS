package com.unisa.sesalab.ods.factory

import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.model.SESALabAccount
import com.unisa.sesalab.ods.model.Seat
import development.kit.reservation.BaseReservation

object ReservationEntityFactory
{
    fun createReservation(baseReservation: BaseReservation,
                          account: SESALabAccount,
                          seat: Seat,
                          name: String): Reservation
    {
        return Reservation(name, baseReservation.start, baseReservation.end, account, seat, false,
            baseReservation.reservationId)
    }
}