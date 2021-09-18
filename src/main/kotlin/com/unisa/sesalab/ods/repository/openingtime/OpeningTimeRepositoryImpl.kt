package com.unisa.sesalab.ods.repository.openingtime

import com.unisa.sesalab.ods.model.PeriodicOpeningTime
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.springframework.stereotype.Repository
import java.time.DayOfWeek
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class OpeningTimeRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<PeriodicOpeningTime, Long>(entityManager), OpeningTimeRepository
{
    override fun findByDayOfWeek(dayOfWeek: DayOfWeek): PeriodicOpeningTime?
    {
        val query = this.em.createQuery("select opentime from PERIODIC_OPENING_TIMES as opentime where opentime.dayOfWeek = :dayOfWeek", PeriodicOpeningTime::class.java)
        query.setParameter("dayOfWeek", dayOfWeek)
        return try {
            query.singleResult
        }
        catch (error: NoResultException)
        {
            null
        }
    }

    override fun insertOpeningTime(periodicOpeningTime: PeriodicOpeningTime)
    {
        this.save(periodicOpeningTime)
    }

    override fun deleteOpeningTimeById(id: Long)
    {
       this.delete(id)
    }

    override fun updateOpeningTime(periodicOpeningTime: PeriodicOpeningTime)
    {
        this.update(periodicOpeningTime)
    }

    override fun findOpeningTimeById(id: Long): PeriodicOpeningTime?
    {
        return this.findById(id)
    }

    override fun findAll(): List<PeriodicOpeningTime>
    {
        val query = this.em.createQuery("select opentime from PERIODIC_OPENING_TIMES as opentime", PeriodicOpeningTime::class.java)
        return query.resultList
    }
}