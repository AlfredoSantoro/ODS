package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.model.Reservation
import java.time.OffsetDateTime

interface ReservationRulesService
{
    @Throws(ReservationConstraintsException::class)
    fun checkReservationOverlaps(userId: Long, start: OffsetDateTime, end: OffsetDateTime)

    @Throws(ReservationConstraintsException::class)
    fun checkAssetAvailability(assetID: Long, start: OffsetDateTime, end: OffsetDateTime)

    @Throws(ReservationConstraintsException::class)
    fun checkNewReservation(reservation: Reservation)

    @Throws(ReservationConstraintsException::class)
    fun checkUpdateReservation(reservation: Reservation)
}