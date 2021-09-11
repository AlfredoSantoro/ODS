package com.unisa.sesalab.ods.repository.seats

import com.unisa.sesalab.ods.dto.SeatDTO
import com.unisa.sesalab.ods.model.Seats
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class SeatRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): SeatRepository<Seats, SeatDTO>
{
     private val logger: Logger = LoggerFactory.getLogger(SeatRepositoryDB2Impl::class.java)

     override fun save(entity: Seats): Long
     {
        val seatId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new seat #$seatId successfully saved")
        return seatId
     }

     override fun update(entityId: Long, data: SeatDTO): Seats
     {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to update Seats")
        val tx = databaseSession.beginTransaction()
        val seat = databaseSession.find(Seats::class.java, entityId)
        seat.name = data.name
        seat.canBeBooked = data.canBeBooked
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to update Seats entity")
        this.logger.info("### AccessAuthorizations #${seat.id} up to date")
        return seat
     }

     override fun findById(entityId: Long): Seats
     {
        return this.em.find(Seats::class.java, entityId)
     }

     override fun delete(entityId: Long)
     {
        this.em.remove(this.findById(entityId))
     }
}