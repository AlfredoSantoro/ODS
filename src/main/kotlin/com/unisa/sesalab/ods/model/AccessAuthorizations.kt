package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity(name = "ACCESS_AUTHORIZATIONS")
class AccessAuthorizations(
        var start: OffsetDateTime,
        var end: OffsetDateTime,
        var reason: String,
        @ManyToOne(fetch = FetchType.LAZY)
        val user: User
)
{
    var granted = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}