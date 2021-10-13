package com.unisa.sesalab.ods.model

import javax.persistence.*

@Entity
data class Seat(
        var name: String,
        var canBeBooked: Boolean,
        @OneToOne(fetch = FetchType.LAZY)
        var tagNfc: TagNfc,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long ? = null
)
{
        override fun toString(): String {
                return "Seat(seatName='$name', seatCanBeBooked=$canBeBooked, tagNfc=$tagNfc, id=$id)"
        }
}