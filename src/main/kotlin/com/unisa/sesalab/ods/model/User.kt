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
        val validUntil: LocalDate,
        @Column(nullable = false)
        val username: String,
        @Column(nullable = false)
        val password: String
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
    override fun toString(): String
    {
        return "User(name='$name', surname='$surname', userType=$userType, email='$email', validUntil=$validUntil, " +
                "username='$username', password='$password', id=$id)"
    }

}