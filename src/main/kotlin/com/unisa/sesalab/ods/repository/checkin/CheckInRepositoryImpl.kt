package com.unisa.sesalab.ods.repository.checkin

import com.unisa.sesalab.ods.model.CheckIn
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

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
}