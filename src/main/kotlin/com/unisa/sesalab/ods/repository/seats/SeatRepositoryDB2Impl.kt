package com.unisa.sesalab.ods.repository.seats

import com.unisa.sesalab.ods.model.Seat
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class SeatRepositoryDB2Impl: AbstractDAO<Seat, Long>(), SeatRepository
{
    private val logger: Logger = LoggerFactory.getLogger(SeatRepositoryDB2Impl::class.java)

    override fun insertSeat(seat: Seat): Seat
    {
        return this.save(seat)
    }

    override fun updateSeat(seat: Seat): Seat
    {
        return this.updateSeat(seat)
    }

    override fun deleteSeat(id: Long)
    {
        this.delete(id)
    }

    override fun findBySeatId(id: Long): Seat?
    {
        return this.findById(id)
    }
}
