package com.unisa.sesalab.ods.model

import development.kit.asset.Seat
import javax.persistence.*

@Entity
data class Seat(
        var seatName: String,
        var seatCanBeBooked: Boolean,
        @OneToOne(fetch = FetchType.LAZY)
        var tagNfc: TagNfc
): Seat(seatName, seatCanBeBooked)
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long ? = null

        override fun toString(): String {
                return "Seat(seatName='$seatName', seatCanBeBooked=$seatCanBeBooked, tagNfc=$tagNfc, id=$id)"
        }
}