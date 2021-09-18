package com.unisa.sesalab.ods.dto

import java.time.OffsetTime

data class PeriodicOpeningTimeUpdateDTO(
        val id: Long,
        val open: OffsetTime,
        val close: OffsetTime
)
