package com.unisa.sesalab.ods.service.authorization

import com.unisa.sesalab.ods.dto.AccessAuthorizationInsertDTO
import com.unisa.sesalab.ods.dto.AccessAuthorizationUpdateDTO
import com.unisa.sesalab.ods.model.AccessAuthorizations
import com.unisa.sesalab.ods.repository.authorizations.AccessAuthRepository
import com.unisa.sesalab.ods.service.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AccessAuthorizationsServiceImpl(
        private val accessAuthRepository: AccessAuthRepository,
        private val userService: UserService
): AccessAuthorizationsService
{
    private val logger: Logger = LoggerFactory.getLogger(AccessAuthorizationsServiceImpl::class.java)

    override fun createAuthorization(authorizationInsertDTO: AccessAuthorizationInsertDTO): AccessAuthorizations?
    {
        val authorizationForUser = this.userService.viewAccount(authorizationInsertDTO.userId)
        return if ( authorizationForUser == null )
        {
            this.logger.error("### user #${authorizationInsertDTO.userId} not found")
            null
        }
        else
        {
            val newAuth = AccessAuthorizations(authorizationInsertDTO, authorizationForUser)
            this.accessAuthRepository.insertAuthorization(newAuth)
            return newAuth
        }
    }

    override fun modifyAuthorization(authorizationUpdateDTO: AccessAuthorizationUpdateDTO): AccessAuthorizations?
    {
        val authToModify = this.accessAuthRepository.findByIdAuthorization(authorizationUpdateDTO.id)
        return if ( authToModify == null )
        {
            this.logger.error("### authorization #${authorizationUpdateDTO.id} not found")
            null
        }
        else
        {
            authToModify.granted = authorizationUpdateDTO.granted
            this.accessAuthRepository.updateAuthorization(authToModify)
        }
    }
}