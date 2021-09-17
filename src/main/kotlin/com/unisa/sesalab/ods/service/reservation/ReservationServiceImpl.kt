package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.dto.ReservationDTO
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import com.unisa.sesalab.ods.service.AssetService
import org.springframework.stereotype.Service

@Service
class ReservationServiceImpl(
        private val reservationRepository: ReservationRepository,
        private val assetService: AssetService,
        private val reservationRulesService: ReservationRulesService
): ReservationService
{
    override fun createReservation(reservationDTO: ReservationDTO)
    {
        val assetToReserve = this.assetService.findAssetById(reservationDTO.assetId)
    }

    override fun updateReservation() {
        TODO("Not yet implemented")
    }

    override fun deleteReservation() {
        TODO("Not yet implemented")
    }

    override fun pauseReservation() {
        TODO("Not yet implemented")
    }

    override fun viewReservationDetail() {
        TODO("Not yet implemented")
    }

    override fun reservationsHistory(): List<Reservation> {
        TODO("Not yet implemented")
    }

    override fun findAllReservationsOnGoing(): List<Reservation> {
        TODO("Not yet implemented")
    }

}