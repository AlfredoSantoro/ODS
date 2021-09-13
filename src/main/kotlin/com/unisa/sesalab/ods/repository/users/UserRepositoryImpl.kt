package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.NoResultException

/**
 *
 */

@Repository
class UserRepositoryImpl: AbstractDAO<User, Long>(), UserRepository
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java)

    override fun findByUsernameIgnoreCase(username: String): User?
    {
        this.logger.info("### finding user by username #$username")
        return try
        {
            val query = this.em.createQuery("select u from User as u where u.username = :username", User::class.java)
            query.setParameter("username", username)
            query.singleResult
        }
        catch (error: NoResultException)
        {
            null
        }
    }

}