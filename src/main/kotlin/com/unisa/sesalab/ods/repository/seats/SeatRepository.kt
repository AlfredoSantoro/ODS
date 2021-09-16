package com.unisa.sesalab.ods.repository.seats

import com.unisa.sesalab.ods.model.Seat

interface SeatRepository
{
    fun insertSeat(seat: Seat): Seat
    fun updateSeat(seat: Seat): Seat
    fun deleteSeat(id: Long)
    fun findBySeatId(id: Long): Seat?
}