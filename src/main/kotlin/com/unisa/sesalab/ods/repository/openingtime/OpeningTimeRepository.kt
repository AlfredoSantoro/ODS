package com.unisa.sesalab.ods.repository.openingtime

import com.unisa.sesalab.ods.model.PeriodicOpeningTime
import java.time.DayOfWeek

interface OpeningTimeRepository
{
    fun findByDayOfWeek(dayOfWeek: DayOfWeek): PeriodicOpeningTime?
    fun insertOpeningTime(periodicOpeningTime: PeriodicOpeningTime)
    fun deleteOpeningTimeById(id: Long)
    fun updateOpeningTime(periodicOpeningTime: PeriodicOpeningTime)
    fun findOpeningTimeById(id: Long): PeriodicOpeningTime?
    fun findAll(): List<PeriodicOpeningTime>
}