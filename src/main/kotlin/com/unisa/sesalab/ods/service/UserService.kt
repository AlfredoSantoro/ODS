package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.repository.users.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
)
{
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun signUpUser(userDTOToSave: UserInsertUpdateDTO): User?
    {
        return if ( this.userRepository.findByUsernameIgnoreCase(userDTOToSave.username) == null )
        {
            val userSaved = this.userRepository.insertUser(userDTOToSave)
            this.logger.info("### user #${userSaved.id} sign up successfully")
            userSaved
        }
        else
        {
            this.logger.error("### cannot sign up user ${userDTOToSave.username} because it already exist")
            null
        }
    }
}
