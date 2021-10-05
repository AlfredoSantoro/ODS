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
        val startPath = this.root.get<OffsetDateTime>("reservationStart")
        val endPath = this.root.get<OffsetDateTime>("reservationEnd")
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
        val columnToFilter = when (columnTypeToFilter)
        {
            IdType.USER_ID -> "user_id"
            IdType.ASSET_ID -> "asset_id"
        }
        val idPath = this.root.get<Long>(columnToFilter)
        val startPath = this.root.get<OffsetDateTime>("start")
        val endPath = this.root.get<OffsetDateTime>("end")
        if ( excludeReservationId !== null )
        {
            val reservationPath = this.root.get<Long>("id")
            this.cq.select(this.root)
                    .where(this.cb.and(this.cb.equal(idPath, columnId), this.cb.notEqual(reservationPath, excludeReservationId),
                            this.cb.or(this.cb.between(startPath, start, end), this.cb.between(endPath, start, end))
                    ))
        }
        else
        {
            this.cq
                    .select(this.root)
                    .where(this.cb.and(this.cb.equal(idPath, columnId),
                            this.cb.or(this.cb.between(startPath, start, end), this.cb.between(endPath, start, end))
                    ))
        }

        return this.session.createQuery(this.cq).resultList
    }
}