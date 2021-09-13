package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.model.User

/**
 * User operations
 */
interface UserRepository
{
    fun findByUsernameIgnoreCase(username: String): User?
}