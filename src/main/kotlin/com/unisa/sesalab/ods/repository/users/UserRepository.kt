package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.repository.BaseCrudRepository

/**
 * User operations
 */
interface UserRepository<T, K>: BaseCrudRepository<T, K>
{
    fun findByUsernameIgnoreCase(username: String): T?
}