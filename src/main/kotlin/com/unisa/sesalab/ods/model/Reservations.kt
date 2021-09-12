package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Reservations(
        var name: String,
        var start: OffsetDateTime,
        var end: OffsetDateTime,
        @ManyToOne(fetch = FetchType.LAZY)
        var user: Users,
        @ManyToOne(fetch = FetchType.LAZY)
        var asset: Asset
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}