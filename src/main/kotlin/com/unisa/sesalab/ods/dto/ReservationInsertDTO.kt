package com.unisa.sesalab.ods.dto

import java.time.OffsetDateTime

data class ReservationInsertDTO(
        val name: String,
        val start: OffsetDateTime,
        val userId: Long,
        val assetId: Long
)