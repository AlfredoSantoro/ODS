package com.unisa.sesalab.ods.model

import java.time.DayOfWeek
import java.time.OffsetTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "LAB_OPENING_TIMES")
class OpeningTime(
        val dayOfWeek: DayOfWeek,
        val open: OffsetTime,
        val close: OffsetTime
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}