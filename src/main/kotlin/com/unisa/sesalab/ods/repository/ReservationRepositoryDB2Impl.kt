package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.model.IReservations
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Qualifier("ReservationRepositoryDB2")
@Repository
class ReservationRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
)
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationRepositoryDB2Impl::class.java)

    fun save(entity: IReservations): Long
    {
        val reservationId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new user #$reservationId successfully saved")
        return reservationId
    }
}