package com.unisa.sesalab.ods.dto

import java.time.temporal.ChronoUnit

data class SettingDTO(
        val name: String,
        val value: Int,
        val description: String,
        val representationUnit: ChronoUnit
)
