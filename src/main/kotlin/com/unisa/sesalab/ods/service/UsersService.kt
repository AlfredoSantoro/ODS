package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.Users
import com.unisa.sesalab.ods.repository.BaseRepository
import com.unisa.sesalab.ods.utils.UserLogicManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UsersService(
        @Qualifier("UserRepositoryDB2")
        private val baseRepository: BaseRepository<Users>
) {
    private val userLogicManager: UserLogicManager<Users> = UserLogicManager(this.baseRepository)
    private val logger: Logger = LoggerFactory.getLogger(UsersService::class.java)

    fun saveUser(userDTO: UserDTO): Long
    {
        val id = this.userLogicManager.saveUser(Users(userDTO))
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

}