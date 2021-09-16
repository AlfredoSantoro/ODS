package com.unisa.sesalab.ods.dto

data class SeatDTO(
        val name: String,
        val canBeBooked: Boolean,
        val tagNfcId: Long
)
{
    var id: Long ?= null
}