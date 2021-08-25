package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Reservations(
        val name: String,
        val start: OffsetDateTime,
        val end: OffsetDateTime,
        @ManyToOne(fetch = FetchType.LAZY)
        val users: Users,
        @ManyToOne(fetch = FetchType.LAZY)
        val seat: Seats
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}