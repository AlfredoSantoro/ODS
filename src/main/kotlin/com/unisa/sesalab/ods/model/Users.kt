package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.enum.UserType
import org.hibernate.annotations.ResultCheckStyle
import org.hibernate.annotations.SQLDelete
import java.time.LocalDate
import javax.persistence.*

@SQLDelete(sql = "UPDATE user set deleted = true where id = ?", check = ResultCheckStyle.COUNT)
class Users(
        @Column(name = "NAME", nullable = false)
        override var name: String,
        @Column(name = "SURNAME", nullable = false)
        override var surname: String,
        @Column(name = "USER_TYPE", nullable = false)
        @Enumerated(EnumType.STRING)
        override var userType: UserType,
        @Column(name = "EMAIL", nullable = false)
        override var email: String,
        @Column(name = "VALID_UNTIL", nullable = false)
        override val validUntil: LocalDate,
        @Column(name = "USERNAME", nullable = false)
        override var username: String,
        @Column(name = "PASSWORD", nullable = false)
        override var password: String
): User
{
    // SOFT DELETE
    @Column(nullable = false)
    private var deleted: Boolean = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
    override fun toString(): String
    {
        return "User(name='$name', surname='$surname', userType=$userType, email='$email', validUntil=$validUntil, " +
                "username='$username', password='$password', id=$id)"
    }

}