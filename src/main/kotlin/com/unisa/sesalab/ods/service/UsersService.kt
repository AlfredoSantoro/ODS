package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.UserLogic
import com.unisa.sesalab.ods.model.Users
import com.unisa.sesalab.ods.repository.BaseRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UsersService(
        // TODO Inject UserRepositoryDB2Impl
        private val baseRepository: BaseRepository<Users, Long>
)
{
    private val userLogic: UserLogic<Users, Long> = UserLogic(this.baseRepository)
    private val logger: Logger = LoggerFactory.getLogger(UsersService::class.java)

    fun saveUser(user: Users)
    {
        this.userLogic.saveUser(user)
        this.logger.info("### user #${user.username} saved successfully")
    }
}