package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.model.CheckIn

interface CheckInService
{
    fun saveCheckIn(checkIn: CheckIn)
    fun findRecentCheckInOfReservation(reservationId: Long): CheckIn?
}