package com.unisa.sesalab.ods.dto

import java.time.OffsetDateTime

data class ReservationDTO(
        val name: String,
        val start: OffsetDateTime,
        val end: OffsetDateTime,
        val userId: Long,
        val assetId: Long
)