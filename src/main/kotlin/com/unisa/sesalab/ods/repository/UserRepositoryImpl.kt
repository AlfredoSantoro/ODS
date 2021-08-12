package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.User
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

    override fun insertUser(userDTO: UserDTO)
    {
        val userToSave = User(userDTO)
        val userId = this.em.unwrap(Session::class.java).save(userToSave) as Long
        this.logger.info("new user #$userId successfully saved")
    }

    // THIS IS A SOFT DELETE. SEE #User entity
    override fun deleteUser(userId: Long)
    {
        val userOnDb = this.em.find(User::class.java, userId)
        this.em.remove(userOnDb)
    }

    override fun findById(id: Long): User
    {
        return this.em.find(User::class.java, id)
    }

}