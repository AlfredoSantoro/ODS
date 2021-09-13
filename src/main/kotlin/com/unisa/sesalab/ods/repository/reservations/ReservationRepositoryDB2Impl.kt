package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.dto.ReservationDTO
import com.unisa.sesalab.ods.enum.IdType
import com.unisa.sesalab.ods.model.Asset
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.model.User
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import javax.persistence.EntityManager

@Qualifier("ReservationRepositoryDB2")
@Repository
class ReservationRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): ReservationRepository<Reservation, ReservationDTO>
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationRepositoryDB2Impl::class.java)

    override fun save(entity: Reservation): Long
    {
        val resId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("### reservation #$resId saved successfully")
        return resId
    }

    override fun update(entityId: Long, data: ReservationDTO): Reservation
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to update Reservation")
        val tx = databaseSession.beginTransaction()
        val reservation = databaseSession.find(Reservation::class.java, entityId)
        val user = databaseSession.find(User::class.java, data.userId)
        val asset = databaseSession.find(Asset::class.java, data.assetId)

        reservation.name = data.name
        reservation.start = data.start
        reservation.end = data.end
        reservation.user = user
        reservation.asset = asset

        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to update Reservation entity")
        this.logger.info("### Reservation #${reservation.id} up to date")
        return reservation
    }

    override fun findById(entityId: Long): Reservation
    {
        return this.em.find(Reservation::class.java, entityId)
    }

    override fun delete(entityId: Long)
    {
        this.em.remove(this.findById(entityId))
    }

    override fun suspendReservation(entityId: Long): Reservation
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to suspend Reservation")
        val tx = databaseSession.beginTransaction()
        val reservation = databaseSession.find(Reservation::class.java, entityId)
        reservation.inPause = true
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to suspend Reservation")
        return reservation
    }

    override fun reservationHistory(from: OffsetDateTime): List<Reservation>
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val cb = session.criteriaBuilder
        val criteriaQuery = cb.createQuery(Reservation::class.java)
        val root = criteriaQuery.from(Reservation::class.java)
        val startPath = root.get<OffsetDateTime>("start")
        val endPath = root.get<OffsetDateTime>("end")
        criteriaQuery
                .select(root)
                .where(cb.and(cb.greaterThanOrEqualTo(startPath, from), cb.lessThanOrEqualTo(endPath, OffsetDateTime.now())))
        return session.createQuery(criteriaQuery).resultList
    }

    override fun findAllReservationsOverlapsBy(idType: IdType,
                                               id: Long,
                                               start: OffsetDateTime,
                                               end: OffsetDateTime): List<Reservation>
    {
        val columnToFiltered = when (idType)
        {
            IdType.USER_ID -> "user_id"
            IdType.ASSET_ID -> "asset_id"
        }
        val session = this.em.unwrap(Session::class.java) as Session
        val cb = session.criteriaBuilder
        val criteriaQuery = cb.createQuery(Reservation::class.java)
        val root = criteriaQuery.from(Reservation::class.java)
        val idPath = root.get<Long>(columnToFiltered)
        val startPath = root.get<OffsetDateTime>("start")
        val endPath = root.get<OffsetDateTime>("end")
        criteriaQuery
                .select(root)
                .where(cb.and(cb.equal(idPath, id),
                       cb.or(cb.between(startPath, start, end), cb.between(endPath, start, end))
                ))
        return session.createQuery(criteriaQuery).resultList
    }
}