package com.unisa.sesalab.ods.repository.authorizations

import com.unisa.sesalab.ods.model.AccessAuthorizations
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class AccessAuthRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<AccessAuthorizations, Long>(entityManager), AccessAuthRepository
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

    override fun findAuthorizationBetween(start: OffsetDateTime, end: OffsetDateTime): AccessAuthorizations?
    {
        val startPath = this.root.get<OffsetDateTime>("start")
        val endPath = this.root.get<OffsetDateTime>("end")
        this.cq.select(this.root)
                .where(this.cb.and(this.cb.lessThanOrEqualTo(startPath, start), this.cb.greaterThanOrEqualTo(endPath, end)))
        return try
        {
            this.session.createQuery(this.cq).singleResult
        }
        catch (error: NoResultException)
        {
            null
        }
    }

    override fun findAuthorizationOverlaps(start: OffsetDateTime, end: OffsetDateTime): List<AccessAuthorizations>
    {
        val startPath = this.root.get<OffsetDateTime>("start")
        val endPath = this.root.get<OffsetDateTime>("end")
        this.cq.select(this.root)
                .where(this.cb.or(this.cb.between(startPath, start, end), this.cb.between(endPath, start, end)))
        return this.session.createQuery(this.cq).resultList
    }
}
