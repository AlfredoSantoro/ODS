package com.unisa.sesalab.ods.repository.seats

import com.unisa.sesalab.ods.model.StudySeat

interface SeatRepository
{
    fun insertSeat(studySeat: StudySeat): StudySeat
    fun updateSeat(studySeat: StudySeat): StudySeat
    fun deleteSeat(id: Long)
    fun findBySeatId(id: Long): StudySeat?
    fun findAllSeats(): List<StudySeat>
}