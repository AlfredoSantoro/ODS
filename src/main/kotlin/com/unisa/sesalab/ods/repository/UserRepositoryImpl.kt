package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl: UserRepository
{
    override fun insertUser(userDTO: UserDTO): User
    {
        val newUser = User(userDTO)
        // TODO newUser to save on DB ( criteriaAPI, JPQL or jdbcTemplate ??? )
        return newUser
    }

    override fun deleteUser(userId: Long): User
    {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): User {
        TODO("Not yet implemented")
    }

}