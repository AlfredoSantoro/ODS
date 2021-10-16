package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.enum.IdType
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.Duration
import java.time.OffsetDateTime
import javax.persistence.EntityManager

@Repository
class ReservationRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<Reservation, Long>(entityManager), ReservationRepository
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationRepositoryImpl::class.java)

    override fun insertReservation(reservation: Reservation) { this.save(reservation) }

    override fun updateReservation(reservation: Reservation) { this.update(reservation) }

    override fun deleteReservationById(id: Long) { this.delete(id) }

    override fun viewReservation(id: Long): Reservation? { return this.findById(id) }

    override fun viewReservationsOnGoing(): List<Reservation>
    {
        val now = OffsetDateTime.now()
        val startPath = this.root.get<OffsetDateTime>("start")
        val endPath = this.root.get<OffsetDateTime>("end")
        this.cq.select(this.root)
                .where(this.cb.and(this.cb.lessThanOrEqualTo(startPath, now), this.cb.greaterThan(endPath, now)))
        return this.session.createQuery(this.cq).resultList
    }

    override fun viewRecentReservations(duration: Duration): List<Reservation>
    {
        val startTime = OffsetDateTime.now().minus(duration)

        val whenStarted = if ( duration.toDays() > 0 )
        {
            OffsetDateTime.of(startTime.toLocalDate().atStartOfDay(), startTime.offset)
        }
        else
        {
            startTime
        }

        val startPath = this.root.get<OffsetDateTime>("start")
        this.cq.select(this.root)
                .where(this.cb.and(this.cb.greaterThan(startPath, whenStarted), this.cb.lessThanOrEqualTo(startPath, OffsetDateTime.now())))
        return this.session.createQuery(this.cq).resultList
    }

    override fun findAllUserReservationsOverlaps(start: OffsetDateTime,
                                                 end: OffsetDateTime,
                                                 userId: Long,
                                                 excludeReservationId: Long?): List<Reservation>
    {
        return this.findAllReservationsOverlapsBy(IdType.USER_ID, userId, start, end, excludeReservationId)
    }

    override fun findAllAssetReservationsOverlaps(start: OffsetDateTime,
                                                  end: OffsetDateTime,
                                                  assetId:Long,
                                                  excludeReservationId: Long?): List<Reservation>
    {
        return this.findAllReservationsOverlapsBy(IdType.ASSET_ID, assetId, start, end, excludeReservationId)
    }

    private fun findAllReservationsOverlapsBy(columnTypeToFilter: IdType,
                                              columnId: Long,
                                              start: OffsetDateTime,
                                              end: OffsetDateTime,
                                              excludeReservationId: Long?): List<Reservation>
    {
        return when (columnTypeToFilter)
        {
            IdType.USER_ID ->
            {
                val q = this.em.createQuery("select res from Reservation as res where res.account.id = :accountID" +
                        " and ((res.start >= :start and res.start < :resEnd) or " +
                        " (res.end > :start and res.end <= :resEnd))",
                    Reservation::class.java)
                q.setParameter("accountID", columnId)
                q.setParameter("start", start)
                q.setParameter("resEnd", end)
                q.resultList
            }
            IdType.ASSET_ID ->
            {
                val q = this.em.createQuery(
                    "select res from Reservation as res where res.studySeatReserved.id = :seatId" +
                        " and ((res.start >= :start and res.start < :resEnd) or " +
                        " (res.end > :start and res.end <= :resEnd))",
                    Reservation::class.java)
                q.setParameter("seatId", columnId)
                q.setParameter("start", start)
                q.setParameter("resEnd", end)
                q.resultList
            }
        }
    }
}