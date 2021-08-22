package com.unisa.sesalab.ods.dto

import com.unisa.sesalab.ods.enum.UserType
import com.unisa.sesalab.ods.model.Users
import java.time.LocalDate

/**
 * Data Transfer Object for Users model
 */
data class UserDTO(
        var name: String,
        var surname: String,
        var userType: UserType,
        var email: String,
        var username: String,
        var password: String,
        var validUntil: LocalDate?
)
{
    constructor(users: Users): this(
            users.name,
            users.surname,
            users.userType,
            users.email,
            users.username,
            users.password,
            users.validUntil
    )
}