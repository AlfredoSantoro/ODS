package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.model.UserLogic
import com.unisa.sesalab.ods.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
)
{
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun saveUser(user: User): User
    {
        this.logger.info("### UserService saving user > $user")
        val userLogic = UserLogic(this.userRepository)
        return userLogic.save(user)
    }
}