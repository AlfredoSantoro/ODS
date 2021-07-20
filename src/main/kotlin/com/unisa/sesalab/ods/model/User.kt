package com.unisa.sesalab.ods.model

import javax.persistence.*

@Entity
class User(
        @Column(nullable = false)
        val name: String,
        @Column(nullable = false)
        val surname: String
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1

    override fun toString(): String
    {
        return "User(name='${this.name}', surname='${this.surname}', id=${this.id})"
    }
}