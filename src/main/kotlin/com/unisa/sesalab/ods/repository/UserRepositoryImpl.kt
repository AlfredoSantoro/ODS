package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.model.Users
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class UserRepositoryImpl(
        private val em: EntityManager
): UserRepository
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java)

    override fun insertUser(user: User): User
    {
        val userId = this.em.unwrap(Session::class.java).save(user) as Long
        this.logger.info("new user #$userId successfully saved")
        return user
    }

    // THIS IS A SOFT DELETE. SEE #User entity
    override fun deleteUser(userId: Long)
    {
        val usersOnDb = this.em.find(Users::class.java, userId)
        this.em.remove(usersOnDb)
    }

    override fun findById(id: Long): Users
    {
        return this.em.find(Users::class.java, id)
    }

}