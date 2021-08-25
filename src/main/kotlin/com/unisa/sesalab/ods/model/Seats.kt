package com.unisa.sesalab.ods.model

import javax.persistence.*

@Entity
class Seats(
        val name: String,
        @Column(name = "CAN_BE_BOOKED", nullable = false)
        val canBeBooked: Boolean,
        @OneToOne(fetch = FetchType.LAZY)
        val tagNfc: TagNfc
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}