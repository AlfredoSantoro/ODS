package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.dto.AccessAuthorizationInsertDTO
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
    constructor(accessAuthorizationInsertDTO: AccessAuthorizationInsertDTO,
                user: User): this(
            accessAuthorizationInsertDTO.start,
            accessAuthorizationInsertDTO.end,
            accessAuthorizationInsertDTO.reason,
            user
    )

    var granted = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
}