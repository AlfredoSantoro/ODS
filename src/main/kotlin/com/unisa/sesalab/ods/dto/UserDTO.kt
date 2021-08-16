package com.unisa.sesalab.ods.dto

import com.unisa.sesalab.ods.enum.UserType
import java.time.LocalDate

/**
 * Data Transfer Object for Users model
 */
data class UserDTO(
        val name: String,
        val surname: String,
        val userType: UserType,
        val email: String,
        val username: String,
        val password: String,
        val validUntil: LocalDate?
)