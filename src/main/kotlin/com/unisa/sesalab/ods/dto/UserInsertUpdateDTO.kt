package com.unisa.sesalab.ods.dto

import com.unisa.sesalab.ods.enum.UserType

/**
 * Data Transfer Object for Users model
 */
data class UserInsertUpdateDTO(
        val id: Long?,
        val name: String,
        val surname: String,
        val userType: UserType,
        val email: String,
        val username: String,
        val plainPassword: String
)