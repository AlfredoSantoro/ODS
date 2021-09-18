package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException

/**
 *
 */

@Repository
class UserRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<User, Long>(entityManager), UserRepository
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java)

    override fun insertUser(userDTO: UserInsertUpdateDTO): User
    {
        return this.save(User(userDTO))
    }

    override fun updateUser(userDTO: UserInsertUpdateDTO): User?
    {
        if ( userDTO.id == null ) return null
        val user = this.findById(userDTO.id)
        return if ( user !== null )
        {
            user.name = userDTO.name
            user.surname = userDTO.surname
            user.username = userDTO.username
            user.userType = userDTO.userType
            user.email = userDTO.email
            user.encodedPassword = DigestUtils.sha256Hex(userDTO.plainPassword)
            this.update(user)
        } else null
    }

    override fun deleteUser(id: Long)
    {
        this.delete(id)
    }

    override fun findUserById(id: Long): User?
    {
        return this.findById(id)
    }

    override fun findByUsernameIgnoreCase(username: String): User?
    {
        this.logger.info("### finding user by username #$username")
        return try
        {
            val query = this.em.createQuery("select u from User as u where lower(u.username)= :username", User::class.java)
            query.setParameter("username", username.lowercase())
            query.singleResult
        }
        catch (error: NoResultException)
        {
            null
        }
    }

}