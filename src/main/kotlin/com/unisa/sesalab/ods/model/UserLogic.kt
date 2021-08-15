package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Class that manages the logic for the Users
 */
class UserLogic(
        private val userRepository: UserRepository
)
{
    private val logger: Logger = LoggerFactory.getLogger(UserLogic::class.java)

    fun save(user: User): User
    {
        val userSaved = this.userRepository.insertUser(user)
        this.logger.info("### user #${userSaved.username} saved successfully")
        return userSaved
    }
}