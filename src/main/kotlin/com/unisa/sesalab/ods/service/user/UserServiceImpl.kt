package com.unisa.sesalab.ods.service.user

import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.repository.users.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val userRepository: UserRepository
): UserService
{
    private val logger: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun signUpUser(userInsertUpdateDTO: UserInsertUpdateDTO): User?
    {
        return if ( this.userRepository.findByUsernameIgnoreCase(userInsertUpdateDTO.username) == null )
        {
            val userSaved = this.userRepository.insertUser(userInsertUpdateDTO)
            this.logger.info("### user #${userSaved.id} sign up successfully")
            userSaved
        }
        else
        {
            this.logger.error("### cannot sign up user ${userInsertUpdateDTO.username} because it already exist")
            null
        }
    }

    override fun deleteAccount(userId: Long)
    {
        this.userRepository.deleteUser(userId)
        this.logger.info("### user $userId deleted successfully")
    }

    override fun viewAccount(userId: Long): User? { return this.userRepository.findUserById(userId) }

    fun findUserByUsername(username: String): User? { return this.userRepository.findByUsernameIgnoreCase(username) }

    override fun updateAccount(userInsertUpdateDTO: UserInsertUpdateDTO): User? { return this.userRepository.updateUser(userInsertUpdateDTO) }
}
