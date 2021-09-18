package com.unisa.sesalab.ods.service.openingtime

import com.unisa.sesalab.ods.dto.PeriodicOpeningTimeDTO
import com.unisa.sesalab.ods.dto.PeriodicOpeningTimeUpdateDTO
import com.unisa.sesalab.ods.model.PeriodicOpeningTime

interface OpeningTimeService
{
    fun insertPeriodicOpeningTime(periodicOpeningTimeDTO: PeriodicOpeningTimeDTO)
    fun updateOpeningTime(openingTimeUpdateDTO: PeriodicOpeningTimeUpdateDTO)
    fun deleteOpeningTimeById(id: Long)
    fun findAll(): List<PeriodicOpeningTime>
}