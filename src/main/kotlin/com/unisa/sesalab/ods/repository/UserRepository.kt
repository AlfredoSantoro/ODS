package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.User

/**
 * USER REPOSITORY OPERATIONS
 */
interface UserRepository
{
    fun insertUser(userDTO: UserDTO)
    fun deleteUser(userId: Long)
    fun findById(id: Long): User
}