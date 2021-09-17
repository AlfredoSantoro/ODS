package com.unisa.sesalab.ods.model

import javax.persistence.*

@Entity
class TagNfc(
        @Column(nullable = false)
        var name: String,
        @Column(unique = true, nullable = false)
        var value: String
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
}