package com.unisa.sesalab.ods.dto

import java.time.OffsetDateTime

data class AccessAuthorizationDTO(
        val start: OffsetDateTime,
        val end: OffsetDateTime,
        val reason: String,
        val granted: Boolean
)