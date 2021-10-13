package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.factory.AccountFactory
import com.unisa.sesalab.ods.model.SESALabAccount
import com.unisa.sesalab.ods.repository.AbstractDAO
import development.kit.user.Account
import development.kit.user.CreateAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class UserRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<SESALabAccount, Long>(entityManager), UserRepository
{
    private val logger: Logger = LoggerFactory.getLogger(UserRepositoryImpl::class.java)

    override fun deleteUser(id: Long) { this.delete(id) }

    override fun findAccountById(accountId: Long): Account?
    {
        val entity = this.findById(accountId)
        return if ( entity !== null )
        {
            AccountFactory.createAccount(entity)
        }
        else null
    }

    override fun findAccountByUsername(username: String): Account?
    {   val entity = this.findByUsernameIgnoreCase(username)
        return if ( entity !== null )
        {
            AccountFactory.createAccount(entity)
        }
        else null
    }

    override fun saveAccount(createAccount: CreateAccount): Account
    {
        return AccountFactory.createAccount(this.save(SESALabAccount(createAccount)))
    }

    override fun updateAccount(account: Account): Account
    {
        return AccountFactory.createAccount(this.update(SESALabAccount(account)))
    }

    override fun findByUsernameIgnoreCase(username: String): SESALabAccount?
    {
        this.logger.info("### finding user by username #$username")
        return try
        {
            val query = this.em.createQuery("select u from ACCOUNT as u where lower(u.username)= :username", SESALabAccount::class.java)
            query.setParameter("username", username.lowercase())
            query.singleResult
        }
        catch (error: NoResultException)
        {
            this.logger.info("account with username #$username not found")
            null
        }
    }
}