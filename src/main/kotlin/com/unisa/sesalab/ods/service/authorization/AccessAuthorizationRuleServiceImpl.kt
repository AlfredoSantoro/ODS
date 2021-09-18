package com.unisa.sesalab.ods.service.authorization

import com.unisa.sesalab.ods.exception.AccessAuthorizationConstraintsException
import com.unisa.sesalab.ods.repository.authorizations.AccessAuthRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class AccessAuthorizationRuleServiceImpl(
        private val accessAuthRepository: AccessAuthRepository
): AccessAuthorizationRulesService
{
    private val logger: Logger = LoggerFactory.getLogger(AccessAuthorizationRuleServiceImpl::class.java)

    override fun checkOverlapsAuthorizations(start: OffsetDateTime, end: OffsetDateTime)
    {
        if ( this.accessAuthRepository.findAuthorizationOverlaps(start, end).isNotEmpty() )
        {
            this.logger.error("### cannot create the authorization because it overlaps with another")
            throw AccessAuthorizationConstraintsException("cannot create the authorization because it overlaps with another")
        }
    }
}