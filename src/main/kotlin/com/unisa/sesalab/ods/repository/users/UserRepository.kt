package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.model.User

/**
 * User operations
 */
interface UserRepository
{
    fun insertUser(userDTO: UserInsertUpdateDTO): User
    fun updateUser(userDTO: UserInsertUpdateDTO): User?
    fun deleteUser(id: Long)
    fun findUserById(id: Long): User?
    fun findByUsernameIgnoreCase(username: String): User?
}