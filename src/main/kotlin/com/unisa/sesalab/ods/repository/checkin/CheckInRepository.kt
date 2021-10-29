package com.unisa.sesalab.ods.repository.checkin

import com.unisa.sesalab.ods.model.CheckIn

interface CheckInRepository
{
    fun saveCheckin(checkIn: CheckIn)
}