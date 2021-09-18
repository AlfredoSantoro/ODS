package com.unisa.sesalab.ods.dto

import java.time.DayOfWeek
import java.time.OffsetTime

data class PeriodicOpeningTimeDTO(
        val dayOfWeek: DayOfWeek,
        val open: OffsetTime,
        val close: OffsetTime
)
