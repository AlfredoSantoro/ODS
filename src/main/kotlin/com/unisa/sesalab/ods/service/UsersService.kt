/*
package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.logic.users.UserLogicManager
import com.unisa.sesalab.ods.model.User
import com.unisa.sesalab.ods.repository.users.UserRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UsersService(
        @Qualifier("UserRepositoryDB2")
        private val userRepository: UserRepository<User, UserDTO>
)
{
    //private val userLogicManager: UserLogicManager<User, UserDTO> = UserLogicManager(this.userRepository)

*/
/*    fun signUpUser(userDTO: UserDTO): Long?
    {
        return this.userLogicManager.signUpUser(User(userDTO), userDTO.username)
    }

    fun findById(userId: Long): User?
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

    fun findByUsername(username: String): User?
    {
        return this.userLogicManager.findUserByUsername(username)
    }*//*


}*/
