package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class CheckIn(
        @ManyToOne(fetch = FetchType.LAZY)
        val reservation: Reservation,
        val time: OffsetDateTime
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
}