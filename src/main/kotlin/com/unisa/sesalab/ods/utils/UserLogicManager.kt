package com.unisa.sesalab.ods.utils

import com.unisa.sesalab.ods.repository.UserRepository

data class UserLogicManager<T, K>(
        private val rep: UserRepository<T, K>
): BasicLogicManager<T, K>(rep)
{
    fun findByUsername(username: String): T?
    {
        return this.rep.findByUsernameIgnoreCase(username)
    }
}

