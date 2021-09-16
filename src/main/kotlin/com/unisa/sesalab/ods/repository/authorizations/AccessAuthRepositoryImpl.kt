package com.unisa.sesalab.ods.repository.authorizations

import com.unisa.sesalab.ods.model.AccessAuthorizations
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class AccessAuthRepositoryImpl: AbstractDAO<AccessAuthorizations, Long>(), AccessAuthRepository
{
    private val logger: Logger = LoggerFactory.getLogger(AccessAuthRepositoryImpl::class.java)

    override fun insertAuthorization(accessAuthorizations: AccessAuthorizations): AccessAuthorizations
    {
        return this.save(accessAuthorizations)
    }

    override fun updateAuthorization(accessAuthorizations: AccessAuthorizations): AccessAuthorizations?
    {
        return this.update(accessAuthorizations)
    }

    override fun findByIdAuthorization(authId: Long): AccessAuthorizations?
    {
        return this.findById(authId)
    }

    override fun deleteAuthorization(authId: Long)
    {
        this.delete(authId)
    }

}
