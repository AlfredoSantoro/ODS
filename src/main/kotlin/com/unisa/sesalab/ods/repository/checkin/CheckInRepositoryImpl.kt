package com.unisa.sesalab.ods.repository.checkin

import com.unisa.sesalab.ods.model.CheckIn
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class CheckInRepositoryImpl(
    entityManager: EntityManager
): AbstractDAO<CheckIn, Long>(entityManager), CheckInRepository
{
    private val logger : Logger = LoggerFactory.getLogger(CheckInRepositoryImpl::class.java)

    override fun saveCheckin(checkIn: CheckIn)
    {
        this.save(checkIn)
        this.logger.info("### check-in saved successfully for reservation #${checkIn.reservation.id}")
    }

    override fun findRecentCheckInOfReservation(reservationId: Long): CheckIn?
    {
        val query = this.em.createQuery("select recent from CheckIn as recent where recent.time = " +
                "(select MAX(maxCheckIn.time) from CheckIn as maxCheckIn where maxCheckIn.reservation.id = :reservationId)",
            CheckIn::class.java)

        query.setParameter("reservationId", reservationId)

        return try
        {
            query.singleResult
        }
        catch (error: NoResultException)
        {
            this.logger.info("### no check-in found for reervation #$reservationId")
            null
        }
    }
}