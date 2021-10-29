package com.unisa.sesalab.ods.service.checkin

import com.unisa.sesalab.ods.model.CheckIn
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.checkin.CheckInRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class CheckInServiceImpl(
    private val checkInRepository: CheckInRepository
): CheckInService
{
    override fun saveCheckIn(reservation: Reservation)
    {
        val checkInToSave = CheckIn(reservation, OffsetDateTime.now())
        this.checkInRepository.saveCheckin(checkInToSave)
    }
}