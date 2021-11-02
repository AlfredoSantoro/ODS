package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.ReservationUpdateDTO
import com.unisa.sesalab.ods.model.Reservation

interface ReservationService
{
    fun createReservation(reservationInsertDTO: ReservationInsertDTO)
    fun updateReservation(reservationUpdateDTO: ReservationUpdateDTO)
    fun deleteReservation(id: Long)
    fun terminateAllReservationsByIds(ids: List<Long>): Int
    fun pauseReservation(id: Long)
    fun findReservationById(id: Long): Reservation?
    fun reservationsHistory(username: String): List<Reservation>
    fun findReservationOnGoingByUser(username: String): Reservation?
    fun viewAllReservationsOnGoing(): List<Reservation>
    fun getReservationsToTerminate(onGoingReservations: List<Reservation>, checkInFrequency: Int): List<Long>
}