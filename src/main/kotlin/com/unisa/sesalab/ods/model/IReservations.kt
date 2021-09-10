package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime

interface IReservations
{
    val name: String
    val start: OffsetDateTime
    val end: OffsetDateTime
    val user: Users
    val seat: Seats
    val id: Long
}