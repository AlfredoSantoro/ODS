package com.unisa.sesalab.ods.dto

import java.time.temporal.ChronoUnit

data class SettingUpdateDTO(
        val value: Int,
        val description: String,
        val representationUnit: ChronoUnit,
        val id: Long
)