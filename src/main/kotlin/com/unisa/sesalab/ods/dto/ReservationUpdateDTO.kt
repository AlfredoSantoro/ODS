package com.unisa.sesalab.ods.dto

import java.time.OffsetDateTime

data class ReservationUpdateDTO(
        val name: String,
        val start: OffsetDateTime,
        val assetId: Long,
        val reservationId: Long
)
