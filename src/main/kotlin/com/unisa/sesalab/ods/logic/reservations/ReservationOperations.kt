package com.unisa.sesalab.ods.logic.reservations

import java.time.OffsetDateTime

interface ReservationOperations<T>
{
    fun createReservation(reservation: T)
    fun updateReservation(reservation: T)
    fun deleteReservation(reservation: T)
    fun suspendReservation(reservation: T)
    fun viewReservationDetails(reservationId: Long): T
    fun reservationHistory(until: OffsetDateTime): Set<T>
}