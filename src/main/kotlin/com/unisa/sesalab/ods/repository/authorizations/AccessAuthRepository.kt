package com.unisa.sesalab.ods.repository.authorizations

import com.unisa.sesalab.ods.model.AccessAuthorizations
import java.time.OffsetDateTime

/**
 * Queste funzionalità corrispondono alle operazioni che possono essere effetuare sulla tabella Authorizations
 */
interface AccessAuthRepository
{
    fun insertAuthorization(accessAuthorizations: AccessAuthorizations): AccessAuthorizations
    fun updateAuthorization(accessAuthorizations: AccessAuthorizations): AccessAuthorizations?
    fun findByIdAuthorization(authId: Long): AccessAuthorizations?
    fun deleteAuthorization(authId: Long)
    fun findAuthorizationBetween(start: OffsetDateTime, end: OffsetDateTime): AccessAuthorizations?
    fun findAuthorizationOverlaps(start: OffsetDateTime, end: OffsetDateTime): List<AccessAuthorizations>
}