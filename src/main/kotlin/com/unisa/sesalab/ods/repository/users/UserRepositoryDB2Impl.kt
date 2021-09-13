package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.model.User
import org.apache.commons.codec.digest.DigestUtils
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException

/**
 * User Repository for DB2 DBMS
 */

@Qualifier("UserRepositoryDB2")
@Repository
class UserRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): UserRepository<User, UserDTO>
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryDB2Impl::class.java)

    override fun save(entity: User): Long
    {
        val userId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new user #$userId successfully saved")
        return userId
    }

    override fun update(entityId: Long, data: UserDTO): User
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to update user")
        val tx = databaseSession.beginTransaction()
        val u = databaseSession.find(User::class.java, entityId)
        u.name = data.name
        u.surname = data.surname
        u.email = data.email
        u.encodedPassword = DigestUtils.sha256Hex(data.plainPassword)
        u.userType = data.userType
        u.username = data.username
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to update user")
        this.logger.info("### user #${u.id} up to date")
        return u
    }

    override fun findById(entityId: Long): User
    {
        return this.em.find(User::class.java, entityId)
    }

    override fun delete(entityId: Long)
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        val tx = databaseSession.beginTransaction()
        val userOnDb = databaseSession.find(User::class.java, entityId)
        userOnDb.deleted = true
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### user #$entityId deleted")
    }

    @Throws(NoResultException::class)
    override fun findByUsernameIgnoreCase(username: String): User?
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val cb = session.criteriaBuilder
        val criteriaQuery = cb.createQuery(User::class.java)
        val root = criteriaQuery.from(User::class.java)
        val path = root.get<String>("username")
        criteriaQuery.select(root).where(cb.equal(cb.lower(path), username.lowercase()))
        return session.createQuery(criteriaQuery).singleResult
    }

}