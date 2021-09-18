package com.unisa.sesalab.ods.service.authorization

import com.unisa.sesalab.ods.exception.AccessAuthorizationConstraintsException
import java.time.OffsetDateTime

interface AccessAuthorizationRulesService
{
    @Throws(AccessAuthorizationConstraintsException::class)
    fun checkOverlapsAuthorizations(start: OffsetDateTime, end: OffsetDateTime)
}