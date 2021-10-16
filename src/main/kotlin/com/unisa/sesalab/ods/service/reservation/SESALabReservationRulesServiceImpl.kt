package com.unisa.sesalab.ods.service.reservation

import com.unisa.sesalab.ods.exception.ReservationConstraintsException
import com.unisa.sesalab.ods.model.PeriodicOpeningTime
import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.repository.authorizations.AccessAuthRepository
import com.unisa.sesalab.ods.repository.openingtime.OpeningTimeRepository
import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import development.kit.asset.Asset
import development.kit.user.Account
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class SESALabReservationRulesServiceImpl(
        private val reservationRepository: ReservationRepository,
        private val accessAuthRepository: AccessAuthRepository,
        private val openingTimeRepository: OpeningTimeRepository
): ReservationRulesService
{
    @Throws(ReservationConstraintsException::class)
    override fun checkNewReservation(reservation: Reservation)
    {
        val userAuth = this.accessAuthRepository.findAuthorizationBetween(reservation.start, reservation.end)
        if ( userAuth !== null )
        {
            if ( userAuth.granted )
            {
                /*
                * Check that the reservation interval is included in the opening times of the laboratory.
                * If the reservation interval is not included in the laboratory opening times then throws an exception
                 */
                val openingTime = this.openingTimeRepository.findByDayOfWeek(reservation.start.dayOfWeek)
                if ( !this.reservationIsIncludedInTheOpeningTimesOfTheLab(reservation, openingTime) )
                {
                    throw ReservationConstraintsException("Cannot create a reservation beyond the lab opening times")
                }
            }
            else
            {
                throw ReservationConstraintsException("reservation not allowed because the authorization #${userAuth.id} has not been granted")
            }
        }
        else
        {
            throw ReservationConstraintsException("reservation not allowed because user does not have authorization to access the lab")
        }
    }

    private fun reservationIsIncludedInTheOpeningTimesOfTheLab(reservation: Reservation, periodicOpeningTime: PeriodicOpeningTime?): Boolean
    {
        return if ( periodicOpeningTime === null ) false
        else
        {
            reservation.start.toOffsetTime() >= periodicOpeningTime.open
                    && reservation.start.toOffsetTime() <= periodicOpeningTime.close
                    && reservation.end.toOffsetTime() <= periodicOpeningTime.close
        }
    }

    private fun isUserReservationsOverlaps(userId: Long, start: OffsetDateTime, end: OffsetDateTime,
                                              excludeReservationId: Long ?= null): Boolean
    {
        return this.reservationRepository.findAllUserReservationsOverlaps(start, end, userId, excludeReservationId).isNotEmpty()
    }

    private fun isAssetReservationsOverlaps(assetID: Long, start: OffsetDateTime,
                                            end: OffsetDateTime, excludeReservationId: Long ?= null): Boolean
    {
        return this.reservationRepository.findAllAssetReservationsOverlaps(start, end, assetID, excludeReservationId).isNotEmpty()
    }

    override fun checkUpdateReservation(reservation: Reservation)
    {
        this.isUserReservationsOverlaps(reservation.account.id!!, reservation.start, reservation.end, reservation.id)
        this.isAssetReservationsOverlaps(reservation.studySeatReserved.id!!, reservation.start, reservation.end, reservation.id)
        if ( this.isOnGoing(reservation.start, reservation.end) )
        {
            throw ReservationConstraintsException("Cannot update a reservation ongoing")
        }
    }

    override fun isAssetAvailable(
        asset: Asset,
        startReservation: OffsetDateTime,
        endReservation: OffsetDateTime
    ): Boolean { return !(this.isAssetReservationsOverlaps(asset.assetId!!, startReservation, endReservation)) }

    override fun isOverlappingUserReservations(
        account: Account,
        startReservation: OffsetDateTime,
        endReservation: OffsetDateTime
    ): Boolean { return this.isUserReservationsOverlaps(account.accountId!!, startReservation, endReservation) }

    private fun isOnGoing(start: OffsetDateTime, end: OffsetDateTime): Boolean
    {
        val now = OffsetDateTime.now()
        return (now >= start && now < end)
    }
}