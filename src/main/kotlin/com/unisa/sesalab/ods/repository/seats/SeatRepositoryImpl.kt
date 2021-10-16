package com.unisa.sesalab.ods.repository.seats

import com.unisa.sesalab.ods.model.StudySeat
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class SeatRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<StudySeat, Long>(entityManager), SeatRepository
{
    private val logger: Logger = LoggerFactory.getLogger(SeatRepositoryImpl::class.java)

    override fun insertSeat(studySeat: StudySeat): StudySeat
    {
        return this.save(studySeat)
    }

    override fun updateSeat(studySeat: StudySeat): StudySeat
    {
        return this.updateSeat(studySeat)
    }

    override fun deleteSeat(id: Long)
    {
        this.delete(id)
    }

    override fun findBySeatId(id: Long): StudySeat?
    {
        return this.findById(id)
    }

    override fun findAllSeats(): List<StudySeat>
    {
        val query = this.em.createQuery("select seat from StudySeat as seat", StudySeat::class.java)
        return query.resultList
    }
}
