package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.model.Users

/**
 * USER REPOSITORY OPERATIONS
 */
interface UserRepository
{
    fun insertUser(user: User): User
    fun deleteUser(userId: Long)
    fun findById(id: Long): Users
    fun updateUser(userToUpdate: User): User
}