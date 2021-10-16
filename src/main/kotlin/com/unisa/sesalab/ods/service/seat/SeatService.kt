package com.unisa.sesalab.ods.service.seat

import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.SeatUpdateDTO
import com.unisa.sesalab.ods.model.StudySeat

interface SeatService
{
    fun createSeat(seatDTO: SeatInsertDTO): StudySeat?
    fun deleteSeat(id: Long)
    fun updateSeat(seatUpdateDTO: SeatUpdateDTO): StudySeat?
    fun findById(id: Long): StudySeat?
    fun findAllSeats(): List<StudySeat>
}