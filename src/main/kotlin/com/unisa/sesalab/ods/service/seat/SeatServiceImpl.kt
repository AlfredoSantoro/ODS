package com.unisa.sesalab.ods.service.seat

import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.SeatUpdateDTO
import com.unisa.sesalab.ods.model.Seat
import com.unisa.sesalab.ods.repository.seats.SeatRepository
import com.unisa.sesalab.ods.service.tagnfc.TagNFCService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SeatServiceImpl(
        private val seatRepository: SeatRepository,
        private val tagNFCService: TagNFCService
): SeatService
{
    private val logger: Logger = LoggerFactory.getLogger(SeatServiceImpl::class.java)

    override fun createSeat(seatDTO: SeatInsertDTO): Seat?
    {
        val tagNFC = this.tagNFCService.viewTagNFC(seatDTO.tagNfcId)
        return if ( tagNFC !== null )
        {
            val seat = Seat(seatDTO.name, seatDTO.canBeBooked, tagNFC)
            this.seatRepository.insertSeat(seat)
        }
        else
        {
            this.logger.error("### cannot create a seat because our tag nfc does not exist")
            null
        }
    }

    override fun deleteSeat(id: Long)
    {
        this.seatRepository.deleteSeat(id)
    }

    override fun updateSeat(seatUpdateDTO: SeatUpdateDTO): Seat?
    {
        val seat = this.seatRepository.findBySeatId(seatUpdateDTO.id)
        return if ( seat !== null )
        {
            val tagNFC = this.tagNFCService.viewTagNFC(seatUpdateDTO.tagNFCId)
            if ( tagNFC !== null )
            {
                seat.name = seatUpdateDTO.name
                seat.canBeBooked = seatUpdateDTO.canBeBooked
                seat.tagNfc = tagNFC
                this.seatRepository.updateSeat(seat)

            }
            else
            {
                this.logger.error("### cannot update seat because the tagNFC does not exist")
                null
            }
        }
        else
        {
            this.logger.error("### cannot update seat #${seatUpdateDTO.id} because it does not exist")
            null
        }
    }

    override fun findById(id: Long): Seat? { return this.seatRepository.findBySeatId(id) }

    override fun findAllSeats(): List<Seat>
    {
        return this.seatRepository.findAllSeats()
    }
}