package com.unisa.sesalab.ods.model

import development.kit.reservation.SeatsReservationWithPause
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Reservation(
    @Column
    var reservationName: String,
    @Column(nullable = false)
    var reservationStart: OffsetDateTime,
    @Column(nullable = false)
    var reservationEnd: OffsetDateTime,
    @ManyToOne(fetch = FetchType.LAZY)
    var sesaLabAccount: SESALabAccount,
    @ManyToOne(fetch = FetchType.LAZY)
    var seatReserved: Seat,
    @Column(name = "IN_PAUSE", nullable = false)
    var paused: Boolean = false
): SeatsReservationWithPause(reservationName, reservationStart, reservationEnd, seatReserved, sesaLabAccount, paused)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null

    override fun toString(): String {
        return "Reservation(reservationName='$reservationName', reservationStart=$reservationStart, " +
                "reservationEnd=$reservationEnd, sesaLabAccount=$sesaLabAccount, seatReserved=$seatReserved," +
                " paused=$paused, id=$id)"
    }
}