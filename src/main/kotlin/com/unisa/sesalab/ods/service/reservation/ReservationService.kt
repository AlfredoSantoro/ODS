package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.dto.ReservationDTO
import com.unisa.sesalab.ods.model.Reservation

interface ReservationService
{
    fun createReservation(reservationDTO: ReservationDTO)
    fun updateReservation()
    fun deleteReservation()
    fun pauseReservation()
    fun viewReservationDetail()
    fun reservationsHistory(): List<Reservation>
    fun findAllReservationsOnGoing(): List<Reservation>
}