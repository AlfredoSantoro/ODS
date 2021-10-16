package com.unisa.sesalab.ods.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Reservation(
    var name: String,
    var start: OffsetDateTime,
    var end: OffsetDateTime,
    @ManyToOne(fetch = FetchType.LAZY)
    var account: SESALabAccount,
    @ManyToOne(fetch = FetchType.LAZY)
    var studySeatReserved: StudySeat,
    @Column(name = "PAUSED")
    var inPause: Boolean = false,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
)
{
    override fun toString(): String {
        return "Reservation(reservationName='$name', reservationStart=$start, " +
                "reservationEnd=$end, sesaLabAccount=$account, seatReserved=$studySeatReserved," +
                " paused=$inPause, id=$id)"
    }
}