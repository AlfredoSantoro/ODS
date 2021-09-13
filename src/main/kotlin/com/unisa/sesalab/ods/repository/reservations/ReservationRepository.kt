package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.enum.IdType
import java.time.OffsetDateTime

interface ReservationRepository<Entity, DataTransferObject>
{
    fun findAllReservationsOverlapsBy(idType: IdType, id: Long, start:OffsetDateTime, end: OffsetDateTime): List<Entity>
    fun suspendReservation(entityId: Long): Entity
    fun reservationHistory(from: OffsetDateTime): List<Entity>
}