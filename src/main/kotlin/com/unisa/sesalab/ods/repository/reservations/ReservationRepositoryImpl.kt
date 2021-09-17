package com.unisa.sesalab.ods.repository.reservations

import com.unisa.sesalab.ods.enum.IdType
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

@Repository
class ReservationRepositoryImpl: AbstractDAO<Reservation, Long>(), ReservationRepository
{
    private val logger: Logger = LoggerFactory.getLogger(ReservationRepositoryImpl::class.java)

    override fun insertReservation(reservation: Reservation) { this.save(reservation) }

    override fun updateReservation(reservation: Reservation) { this.update(reservation) }

    override fun deleteReservationById(id: Long) { this.delete(id) }

    override fun viewReservation(id: Long): Reservation? { return this.findById(id) }

    override fun viewReservationsOnGoing(): List<Reservation>
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val cb = session.criteriaBuilder
        val criteriaQuery = cb.createQuery(Reservation::class.java)
        val root = criteriaQuery.from(Reservation::class.java)
        val now = OffsetDateTime.now()
        val startPath = root.get<OffsetDateTime>("start")
        val endPath = root.get<OffsetDateTime>("end")
        criteriaQuery
                .select(root)
                .where(cb.and(cb.lessThanOrEqualTo(startPath, now), cb.greaterThan(endPath, now)))
        return session.createQuery(criteriaQuery).resultList
    }

    override fun viewRecentReservations(howManyDaysBefore: Int): List<Reservation>
    {
        val startLimit = OffsetDateTime.now().minusDays(howManyDaysBefore.toLong())
        val limitAtStartOfDay = OffsetDateTime.of(startLimit.toLocalDate().atStartOfDay(), startLimit.offset)
        val limitAtEndOfDay = OffsetDateTime.of(LocalDate.now(), LocalTime.of(23, 59), startLimit.offset)
        val session = this.em.unwrap(Session::class.java) as Session
        val cb = session.criteriaBuilder
        val criteriaQuery = cb.createQuery(Reservation::class.java)
        val root = criteriaQuery.from(Reservation::class.java)
        val startPath = root.get<OffsetDateTime>("start")
        criteriaQuery
                .select(root)
                .where(cb.and(cb.greaterThan(startPath, limitAtStartOfDay), cb.lessThanOrEqualTo(startPath, limitAtEndOfDay)))
        return session.createQuery(criteriaQuery).resultList
    }

    override fun findAllReservationsOverlapsBy(idType: IdType, idByFilter: Long,
                                               start: OffsetDateTime,
                                               end: OffsetDateTime): List<Reservation>
    {
        val columnToFilter = when (idType)
        {
            IdType.USER_ID -> "user_id"
            IdType.ASSET_ID -> "asset_id"
        }
        val session = this.em.unwrap(Session::class.java) as Session
        val cb = session.criteriaBuilder
        val criteriaQuery = cb.createQuery(Reservation::class.java)
        val root = criteriaQuery.from(Reservation::class.java)
        val idPath = root.get<Long>(columnToFilter)
        val startPath = root.get<OffsetDateTime>("start")
        val endPath = root.get<OffsetDateTime>("end")
        criteriaQuery
                .select(root)
                .where(cb.and(cb.equal(idPath, idByFilter),
                       cb.or(cb.between(startPath, start, end), cb.between(endPath, start, end))
                ))
        return session.createQuery(criteriaQuery).resultList
    }
}