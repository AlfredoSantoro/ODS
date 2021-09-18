package com.unisa.sesalab.ods.repository.reservations

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
    fun findAllUserReservationsOverlaps(start:OffsetDateTime, end: OffsetDateTime, userId: Long, excludeReservationId: Long?): List<Reservation>
    fun findAllAssetReservationsOverlaps(start:OffsetDateTime, end: OffsetDateTime, assetId: Long, excludeReservationId: Long?): List<Reservation>
}