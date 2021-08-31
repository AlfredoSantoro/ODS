package com.unisa.sesalab.ods.utils

import com.unisa.sesalab.ods.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.persistence.NoResultException

data class UserLogicManager<T, K>(
        private val rep: UserRepository<T, K>
): BasicLogicManager<T, K>(rep)
{
    private val logger: Logger = LoggerFactory.getLogger(UserLogicManager::class.java)

    fun findUserByUsername(username: String): T?
    {
        return try
        {
            this.rep.findByUsernameIgnoreCase(username)
        }
        catch (noResultException: NoResultException)
        {
            this.logger.error("### user #$username not found ")
            null
        }
    }

    fun signUpUser(user: T, username: String): Long?
    {
        return if ( this.findUserByUsername(username) == null )
        {
            val userID = this.save(user)
            this.logger.info("### user #$user sign up successfully")
            userID
        }
        else
        {
            this.logger.error("### cannot sign up user $user because it already exist")
            null
        }
    }

    fun findUserById(userId: Long): T?
    {
        return try
        {
            this.findById(userId)
        }
        catch (err: Exception)
        {
            this.logger.info("### user #$userId not found ")
            null
        }
    }

    fun deleteUserById(userId: Long)
    {
        try
        {
            this.delete(userId)
        }
        catch (err: Exception)
        {
            this.logger.info("### user #$userId not found ")
        }
    }

    fun updateUser(userId: Long, data: K)
    {
        try
        {
            this.update(userId, data)
        }
        catch (err: Exception)
        {
            this.logger.error("### user #$userId not updated. ${err.message}\n Try again.")
        }
    }
}

