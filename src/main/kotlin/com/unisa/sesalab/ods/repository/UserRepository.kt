package com.unisa.sesalab.ods.repository

/**
 * User operations
 */
interface UserRepository<T, K>: BaseCrudRepository<T,K>
{
    fun findByUsernameIgnoreCase(username: String): T?
}