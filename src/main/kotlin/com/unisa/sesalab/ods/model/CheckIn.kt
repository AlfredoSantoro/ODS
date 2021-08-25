package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class CheckIn(
        @ManyToOne(fetch = FetchType.LAZY)
        val reservations: Reservations,
        val time: OffsetDateTime
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}