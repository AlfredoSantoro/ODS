package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.model.Reservations
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
): ReservationRepository<Reservations, Any>
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationRepositoryDB2Impl::class.java)

    override fun save(entity: Reservations): Long
    {
        val resId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("### reservation #$resId saved successfully")
        return resId
    }

    override fun update(entityId: Long, data: Any): Reservations
    {
        TODO("Not yet implemented")
    }

    override fun findById(entityId: Long): Reservations
    {
        return this.em.find(Reservations::class.java, entityId)
    }

    override fun delete(entityId: Long) {
        TODO("Not yet implemented")
    }
}