package com.unisa.sesalab.ods.dto

data class SeatInsertDTO(
        val name: String,
        val canBeBooked: Boolean,
        val tagNfcId: Long
)