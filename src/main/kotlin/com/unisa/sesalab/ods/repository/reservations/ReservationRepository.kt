package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.enum.IdType
import com.unisa.sesalab.ods.model.Reservation
import java.time.Duration
import java.time.OffsetDateTime

interface ReservationRepository
{
    fun insertReservation(reservation: Reservation)
    fun updateReservation(reservation: Reservation)
    fun deleteReservationById(id :Long)
    fun viewReservation(id: Long): Reservation?
    fun viewReservationsOnGoing(): List<Reservation>
    fun viewRecentReservations(duration: Duration): List<Reservation>
    fun findAllReservationsOverlapsBy(idType: IdType, idByFilter: Long, start:OffsetDateTime,
                                      end: OffsetDateTime, excludeReservationId: Long?): List<Reservation>
}