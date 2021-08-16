package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.model.Users
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

/**
 * User Repository for DB2 DBMS
 */
@Repository
class UserRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): BaseRepository<Users, Long>
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryDB2Impl::class.java)

    override fun save(entity: Users)
    {
        val userId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new user #$userId successfully saved")
    }

    override fun update(entityToUpdate: Users): Users
    {
        val userUpdated = this.em.unwrap(Session::class.java).merge(entityToUpdate) as Users
        this.logger.info("### user #${userUpdated.id} up to date")
        return userUpdated
    }

    override fun findById(entityId: Long): Users
    {
        return this.em.find(Users::class.java, entityId)
    }

    // THIS IS A SOFT DELETE
    override fun delete(entityId: Long)
    {
        val usersOnDb = this.findById(entityId)
        usersOnDb.deleted = true
        this.em.persist(usersOnDb)
        this.logger.info("### user #$entityId deleted")
    }

}