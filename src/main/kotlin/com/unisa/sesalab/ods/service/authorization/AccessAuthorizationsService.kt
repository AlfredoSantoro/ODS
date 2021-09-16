package com.unisa.sesalab.ods.service.authorization

import com.unisa.sesalab.ods.dto.AccessAuthorizationInsertDTO
import com.unisa.sesalab.ods.dto.AccessAuthorizationUpdateDTO
import com.unisa.sesalab.ods.model.AccessAuthorizations

/**
 * Queste funzionalità corrispondono ai requisiti funzionali
 */
interface AccessAuthorizationsService
{
    fun createAuthorization(authorizationInsertDTO: AccessAuthorizationInsertDTO): AccessAuthorizations?
    fun modifyAuthorization(authorizationUpdateDTO: AccessAuthorizationUpdateDTO): AccessAuthorizations?
}