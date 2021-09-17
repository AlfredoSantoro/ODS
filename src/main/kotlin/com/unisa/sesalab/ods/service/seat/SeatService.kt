package com.unisa.sesalab.ods.service.seat

import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.SeatUpdateDTO
import com.unisa.sesalab.ods.model.Seat

interface SeatService
{
    fun createSeat(seatDTO: SeatInsertDTO): Seat?
    fun deleteSeat(id: Long)
    fun updateSeat(seatUpdateDTO: SeatUpdateDTO): Seat?
    fun findAllSeats(): List<Seat>
}