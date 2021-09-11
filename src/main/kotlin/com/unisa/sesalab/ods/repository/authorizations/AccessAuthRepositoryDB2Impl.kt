package com.unisa.sesalab.ods.repository.authorizations

import com.unisa.sesalab.ods.dto.AccessAuthorizationDTO
import com.unisa.sesalab.ods.model.AccessAuthorizations
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class AccessAuthRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): AccessAuthRepository<AccessAuthorizations, AccessAuthorizationDTO>
{
    private val logger: Logger = LoggerFactory.getLogger(AccessAuthRepositoryDB2Impl::class.java)

    override fun save(entity: AccessAuthorizations): Long
    {
        val accessAuthId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new authorizations #$accessAuthId successfully saved")
        return accessAuthId
    }

    override fun update(entityId: Long, data: AccessAuthorizationDTO): AccessAuthorizations
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to update AccessAuthorizations")
        val tx = databaseSession.beginTransaction()
        val accessAuthorizations = databaseSession.find(AccessAuthorizations::class.java, entityId)
        accessAuthorizations.start = data.start
        accessAuthorizations.end = data.end
        accessAuthorizations.reason = data.reason
        accessAuthorizations.granted = data.granted
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to update AccessAuthorizations entity")
        this.logger.info("### AccessAuthorizations #${accessAuthorizations.id} up to date")
        return accessAuthorizations
    }

    override fun findById(entityId: Long): AccessAuthorizations
    {
        return this.em.find(AccessAuthorizations::class.java, entityId)
    }

    override fun delete(entityId: Long)
    {
        this.em.remove(this.findById(entityId))
    }

}