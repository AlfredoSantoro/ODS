package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.Users
import com.unisa.sesalab.ods.repository.UserRepository
import com.unisa.sesalab.ods.utils.UserLogicManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UsersService(
        @Qualifier("UserRepositoryDB2")
        private val userRepository: UserRepository<Users, UserDTO>
)
{
    private val userLogicManager: UserLogicManager<Users, UserDTO> = UserLogicManager(this.userRepository)
    private val logger: Logger = LoggerFactory.getLogger(UsersService::class.java)

    fun saveUser(userDTO: UserDTO): Long
    {
        val id = this.userLogicManager.save(Users(userDTO))
        this.logger.info("### user #$id saved successfully")
        return id
    }

    fun findById(userId: Long): Users
    {
        return this.userLogicManager.findById(userId)
    }

    fun deleteById(userId: Long)
    {
        this.userLogicManager.delete(userId)
    }

    fun update(userId: Long, data: UserDTO)
    {
        this.userLogicManager.update(userId, data)
    }

    fun findByUsername(username: String): Users?
    {
        return this.userLogicManager.findByUsername(username)
    }

}