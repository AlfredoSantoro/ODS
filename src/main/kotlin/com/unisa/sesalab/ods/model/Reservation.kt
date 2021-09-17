package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.exception.IllegalReservationException
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Reservation(
        var name: String,
        var start: OffsetDateTime,
        var end: OffsetDateTime,
        @ManyToOne(fetch = FetchType.LAZY)
        var user: User,
        @ManyToOne(fetch = FetchType.LAZY)
        var asset: Asset
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null

    @Column(name = "IN_PAUSE")
    var inPause: Boolean = false

    init
    {
        if ( this.start >= this.end ) throw IllegalReservationException("Cannot create a reservation because start is after that end")
        if ( this.start.dayOfWeek !== this.end.dayOfWeek ) throw IllegalReservationException("Cannot create a multi-day reservation")
        if ( this.end.dayOfWeek < OffsetDateTime.now().dayOfWeek )
        {
            throw IllegalReservationException("Cannot create reservations in the past")
        }
    }
}