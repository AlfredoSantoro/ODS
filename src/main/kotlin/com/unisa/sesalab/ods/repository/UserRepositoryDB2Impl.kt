package com.unisa.sesalab.ods.repository

import com.unisa.sesalab.ods.dto.UserDTO
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

@Qualifier("UserRepositoryDB2")
@Repository
class UserRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): BaseCrudRepository<Users, UserDTO>
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryDB2Impl::class.java)

    override fun save(entity: Users): Long
    {
        val userId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new user #$userId successfully saved")
        return userId
    }

    override fun update(entityId: Long, data: UserDTO): Users
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to update user")
        val tx = databaseSession.beginTransaction()
        val u = databaseSession.find(Users::class.java, entityId)
        u.name = data.name
        u.surname = data.surname
        u.email = data.email
        u.validUntil = data.validUntil ?: u.validUntil
        u.password = data.password
        u.userType = data.userType
        u.username = data.username
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to update user")
        this.logger.info("### user #${u.id} up to date")
        return u
    }

    override fun findById(entityId: Long): Users
    {
        return this.em.find(Users::class.java, entityId)
    }

    override fun delete(entityId: Long)
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        val tx = databaseSession.beginTransaction()
        val usersOnDb = databaseSession.find(Users::class.java, entityId)
        usersOnDb.deleted = true
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### user #$entityId deleted")
    }

}