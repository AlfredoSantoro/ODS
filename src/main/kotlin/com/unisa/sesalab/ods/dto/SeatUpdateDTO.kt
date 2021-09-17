package com.unisa.sesalab.ods.dto

data class SeatUpdateDTO(
        val id: Long,
        val name: String,
        val canBeBooked: Boolean,
        val tagNFCId: Long
)