package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.model.CheckIn
import com.unisa.sesalab.ods.repository.checkin.CheckInRepository
import org.springframework.stereotype.Service

@Service
class CheckInServiceImpl(
    private val checkInRepository: CheckInRepository
): CheckInService
{
    override fun saveCheckIn(checkIn: CheckIn) { this.checkInRepository.saveCheckin(checkIn) }

    override fun findRecentCheckInOfReservation(reservationId: Long): CheckIn?
    {
        return this.checkInRepository.findRecentCheckInOfReservation(reservationId)
    }
}