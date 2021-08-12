package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

// TODO create interface for userService
@Service
class UserService(private val userRepository: UserRepository)
{
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun saveUser(userDTO: UserDTO): User
    {
        val userSaved = this.userRepository.insertUser(userDTO)
        this.logger.info("### user #${userSaved.id} saved successfully")
        return userSaved
    }
}