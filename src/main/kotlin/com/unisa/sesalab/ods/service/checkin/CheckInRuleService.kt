package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.dto.CheckInDTO
import com.unisa.sesalab.ods.model.Reservation

interface CheckInRuleService
{
    fun checkNewCheckIn(checkInDTO: CheckInDTO, userLogged: String): Reservation
}