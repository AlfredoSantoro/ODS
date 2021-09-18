package com.unisa.sesalab.ods.repository.seats

import com.unisa.sesalab.ods.model.Seat
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class SeatRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<Seat, Long>(entityManager), SeatRepository
{
    private val logger: Logger = LoggerFactory.getLogger(SeatRepositoryImpl::class.java)

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

    override fun findAllSeats(): List<Seat>
    {
        val query = this.em.createQuery("select seat from SEAT as seat", Seat::class.java)
        return query.resultList
    }
}
