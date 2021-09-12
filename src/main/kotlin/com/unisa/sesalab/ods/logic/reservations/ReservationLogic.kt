package com.unisa.sesalab.ods.logic.reservations

import java.time.OffsetDateTime

interface ReservationLogic<Entity>
{
    fun validReservation(reservation: Entity): Boolean
    fun updateReservation(reservation: Entity)
    fun deleteReservation(reservation: Entity)
    fun suspendReservation(reservation: Entity)
    fun viewReservationDetails(reservationId: Long): Entity
    fun reservationHistory(until: OffsetDateTime): Set<Entity>
}