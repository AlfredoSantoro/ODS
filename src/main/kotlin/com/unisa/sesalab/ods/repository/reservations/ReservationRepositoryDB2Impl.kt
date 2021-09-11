package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.model.IReservations
import com.unisa.sesalab.ods.model.Reservations
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
): ReservationRepository<IReservations, Any>
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationRepositoryDB2Impl::class.java)

    override fun save(entity: IReservations): Long {
        TODO("Not yet implemented")
    }

    override fun update(entityId: Long, data: Any): Reservations {
        TODO("Not yet implemented")
    }

    override fun findById(entityId: Long): Reservations {
        TODO("Not yet implemented")
    }

    override fun delete(entityId: Long) {
        TODO("Not yet implemented")
    }
}