package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.logic.users.UserLogicManager
import com.unisa.sesalab.ods.model.Users
import com.unisa.sesalab.ods.repository.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UsersService(
        @Qualifier("UserRepositoryDB2")
        private val userRepository: UserRepository<Users, UserDTO>
)
{
    private val userLogicManager: UserLogicManager<Users, UserDTO> = UserLogicManager(this.userRepository)

    fun signUpUser(userDTO: UserDTO): Long?
    {
        return this.userLogicManager.signUpUser(Users(userDTO), userDTO.username)
    }

    fun findById(userId: Long): Users?
    {
        return this.userLogicManager.findUserById(userId)
    }

    fun deleteById(userId: Long)
    {
        this.userLogicManager.deleteUserById(userId)
    }

    fun update(userId: Long, data: UserDTO)
    {
        this.userLogicManager.updateUser(userId, data)
    }

    fun findByUsername(username: String): Users?
    {
        return this.userLogicManager.findUserByUsername(username)
    }

}