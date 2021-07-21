package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.enum.UserType
import java.time.LocalDate
import javax.persistence.*

@Entity
class User(
        @Column(nullable = false)
        val name: String,
        @Column(nullable = false)
        val surname: String,
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        val userType: UserType,
        @Column(nullable = false)
        val email: String,
        @Column(nullable = false)
        val validUntil: LocalDate
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