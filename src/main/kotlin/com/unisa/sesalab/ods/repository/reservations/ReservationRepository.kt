package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.model.Reservation
import java.time.Duration
import java.time.OffsetDateTime

interface ReservationRepository
{
    fun insertReservation(reservation: Reservation)
    fun updateReservation(reservation: Reservation)
    fun deleteReservationById(id :Long)
    fun terminateAllReservationsById(ids :List<Long>): Int
    fun viewReservation(id: Long): Reservation?
    fun findReservationOnGoingByUser(username: String): Reservation?
    fun viewAllReservationsOnGoing(): List<Reservation>
    fun viewRecentUserReservations(duration: Duration, username: String): List<Reservation>
    fun findAllUserReservationsOverlaps(start:OffsetDateTime, end: OffsetDateTime, userId: Long, excludeReservationId: Long?): List<Reservation>
    fun findAllAssetReservationsOverlaps(start:OffsetDateTime, end: OffsetDateTime, assetId: Long, excludeReservationId: Long?): List<Reservation>
}