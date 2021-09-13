package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import org.apache.commons.codec.digest.DigestUtils
import javax.persistence.*

@Entity
class User(
        @Column(name = "NAME", nullable = false)
        var name: String,
        @Column(name = "SURNAME", nullable = false)
        var surname: String,
        @Column(name = "USER_TYPE", nullable = false)
        @Enumerated(EnumType.STRING)
        var userType: UserType,
        @Column(name = "EMAIL", nullable = false)
        var email: String,
        @Column(name = "USERNAME", nullable = false, unique = true)
        var username: String,
        @Column(name = "PASSWORD", nullable = false)
        var encodedPassword: String
)
{
    constructor(userDTO: UserDTO): this(
            userDTO.name,
            userDTO.surname,
            userDTO.userType,
            userDTO.email,
            userDTO.username,
            DigestUtils.sha256Hex(userDTO.plainPassword)
    )

    // SOFT DELETE
    @Column(nullable = false)
    var deleted: Boolean = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1

    override fun toString(): String
    {
        return "User(name='$name', surname='$surname', userType=$userType, email='$email', " +
                "username='$username', password='$encodedPassword', id=$id)"
    }

}