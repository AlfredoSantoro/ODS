package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.enum.IdType
import com.unisa.sesalab.ods.repository.BaseCrudRepository
import java.time.OffsetDateTime

interface ReservationRepository<Entity, DataTransferObject>: BaseCrudRepository<Entity, DataTransferObject>
{
    fun findAllReservationsOverlapsBy(idType: IdType, id: Long, start:OffsetDateTime, end: OffsetDateTime): List<Entity>
    fun suspendReservation(entityId: Long): Entity
    fun reservationHistory(from: OffsetDateTime): List<Entity>
}