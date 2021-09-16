package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.SeatDTO
import com.unisa.sesalab.ods.model.Seat

interface SeatService
{
    fun createSeat(seatDTO: SeatDTO): Seat?
    fun deleteSeat(id: Long)
    fun updateSeat(seatDTO: SeatDTO): Seat?
    fun findAllSeats(): List<Seat>
}