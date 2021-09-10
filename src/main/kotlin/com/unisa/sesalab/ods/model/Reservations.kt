package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Reservations(
        override val name: String,
        override val start: OffsetDateTime,
        override val end: OffsetDateTime,
        @ManyToOne(fetch = FetchType.LAZY)
        override val user: Users,
        @ManyToOne(fetch = FetchType.LAZY)
        override val seat: Seats
): IReservations
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = -1
}