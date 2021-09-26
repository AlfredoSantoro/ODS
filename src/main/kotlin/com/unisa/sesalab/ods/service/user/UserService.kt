package com.unisa.sesalab.ods.service.user

import com.unisa.sesalab.ods.model.SESALabAccount
import development.kit.user.CreateAccount
import development.kit.user.UpdateAccount

interface UserService
{
    fun signUpUser(createAccount: CreateAccount): SESALabAccount
    fun viewAccount(userId: Long): SESALabAccount?
    fun updateAccount(updateAccount: UpdateAccount): SESALabAccount
    fun deleteAccount(userId: Long)
}