package com.unisa.sesalab.ods.dto

import java.time.OffsetDateTime

data class AccessAuthorizationInsertDTO(
        val start: OffsetDateTime,
        val end: OffsetDateTime,
        val reason: String,
        val userId: Long
)