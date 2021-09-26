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
        val sesaLabAccount: SESALabAccount
)
{
    constructor(accessAuthorizationInsertDTO: AccessAuthorizationInsertDTO,
                sesaLabAccount: SESALabAccount): this(
            accessAuthorizationInsertDTO.start,
            accessAuthorizationInsertDTO.end,
            accessAuthorizationInsertDTO.reason,
            sesaLabAccount
    )

    var granted = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
}