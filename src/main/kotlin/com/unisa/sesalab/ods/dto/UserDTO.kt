package com.unisa.sesalab.ods.dto

import com.unisa.sesalab.ods.enum.UserType

/**
 * Data Transfer Object for Users model
 */
data class UserDTO(
        var name: String,
        var surname: String,
        var userType: UserType,
        var email: String,
        var username: String,
        var plainPassword: String
)