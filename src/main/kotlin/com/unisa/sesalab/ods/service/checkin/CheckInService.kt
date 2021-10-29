package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.model.Reservation

interface CheckInService
{
    fun saveCheckIn(reservation: Reservation)
}