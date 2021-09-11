package com.unisa.sesalab.ods.model

import javax.persistence.*

@Entity
class Seats(
        @Column(unique = true)
        var name: String,
        @Column(name = "CAN_BE_BOOKED", nullable = false)
        var canBeBooked: Boolean,
        @OneToOne(fetch = FetchType.LAZY)
        var tagNfc: TagNfc
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}