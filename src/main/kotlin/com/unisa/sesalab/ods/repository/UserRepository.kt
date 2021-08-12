package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.User

interface UserRepository
{
    fun insertUser(userDTO: UserDTO): User
    fun deleteUser(userId: Long): User
    fun findById(id: Long): User
}