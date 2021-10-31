package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.dto.CheckInDTO
import com.unisa.sesalab.ods.model.Reservation
import development.kit.checkin.CheckInRules

interface CheckInRuleService: CheckInRules
{
    fun checkNewCheckIn(checkInDTO: CheckInDTO, userLogged: String): Reservation
}