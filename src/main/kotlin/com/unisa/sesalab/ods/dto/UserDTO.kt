package com.unisa.sesalab.ods.dto

import com.unisa.sesalab.ods.enum.UserType

data class UserDTO(
        val name: String,
        val surname: String,
        val userType: UserType,
        val email: String,
        val username: String,
        val password: String
)
