package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.model.SESALabAccount
import development.kit.user.IStorage

interface UserRepository: IStorage
{
    fun deleteUser(id: Long)
    fun findByUsernameIgnoreCase(username: String): SESALabAccount
}