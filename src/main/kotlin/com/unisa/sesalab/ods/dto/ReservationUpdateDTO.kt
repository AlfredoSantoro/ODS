package com.unisa.sesalab.ods.dto

import java.time.OffsetDateTime

data class ReservationUpdateDTO(
        val name: String,
        val start: OffsetDateTime,
        val seatId: Long,
        val reservationId: Long
)
