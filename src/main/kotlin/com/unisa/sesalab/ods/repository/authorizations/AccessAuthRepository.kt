package com.unisa.sesalab.ods.repository.authorizations

import com.unisa.sesalab.ods.model.AccessAuthorizations

/**
 * Queste funzionalità corrispondono alle operazioni che possono essere effetuare sulla tabella Authorizations
 */
interface AccessAuthRepository
{
    fun insertAuthorization(accessAuthorizations: AccessAuthorizations): AccessAuthorizations
    fun updateAuthorization(accessAuthorizations: AccessAuthorizations): AccessAuthorizations?
    fun findByIdAuthorization(authId: Long): AccessAuthorizations?
    fun deleteAuthorization(authId: Long)
}