package com.unisa.sesalab.ods.model

import java.time.temporal.ChronoUnit
import javax.persistence.*

@Entity
class Setting(
        @Column(unique = true, nullable = false)
        val name: String,
        @Column(nullable = false)
        var value: Int,
        @Column(nullable = false)
        var description: String,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var representationUnit: ChronoUnit
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null
}