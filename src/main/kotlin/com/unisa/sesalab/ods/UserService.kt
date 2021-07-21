package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.dao.UserDAO
import com.unisa.sesalab.ods.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userDAO: UserDAO
)
{
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun saveUser(userToSave: User): User
    {
        this.logger.info("### user to save $userToSave")
        val userSaved = this.userDAO.save(userToSave)
        this.logger.info("### user #${userSaved.id} saved successfully")
        return userSaved
    }
}