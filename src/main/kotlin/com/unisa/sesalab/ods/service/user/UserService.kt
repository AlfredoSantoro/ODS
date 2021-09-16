package com.unisa.sesalab.ods.service.user

import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.model.User

interface UserService
{
    fun signUpUser(userInsertUpdateDTO: UserInsertUpdateDTO): User?
    fun viewAccount(userId: Long): User?
    fun updateAccount(userInsertUpdateDTO: UserInsertUpdateDTO): User?
    fun deleteAccount(userId: Long)
}