package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.SeatDTO
import com.unisa.sesalab.ods.model.Seat
import org.springframework.stereotype.Service

@Service
class SeatServiceImpl(

): SeatService
{
    override fun createSeat(seatDTO: SeatDTO): Seat? {
        TODO("Not yet implemented")
    }

    override fun deleteSeat(id: Long) {
        TODO("Not yet implemented")
    }

    override fun updateSeat(seatDTO: SeatDTO): Seat? {
        TODO("Not yet implemented")
    }

    override fun findAllSeats(): List<Seat> {
        TODO("Not yet implemented")
    }

}