package com.unisa.sesalab.ods.repository.users

import com.unisa.sesalab.ods.model.SESALabAccount
import com.unisa.sesalab.ods.repository.AbstractDAO
import development.kit.exception.IllegalAccountException
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

    override fun findAccountById(accountId: Long): Account? { return this.findById(accountId) }

    override fun findAccountByUsername(username: String): Account? { return this.findByUsernameIgnoreCase(username) }

    override fun saveAccount(createAccount: CreateAccount): Account
    {
        val sesaLabAccount = SESALabAccount(createAccount)
        val sesaLabAccountSaved = this.save(sesaLabAccount)
        sesaLabAccountSaved.accountId = sesaLabAccountSaved.id
        return sesaLabAccountSaved
    }

    override fun updateAccount(account: Account): Account { return this.update(SESALabAccount(account)) }

    override fun findByUsernameIgnoreCase(username: String): SESALabAccount
    {
        this.logger.info("### finding user by username #$username")
        return try
        {
            val query = this.em.createQuery("select u from ACCOUNT as u where lower(u.accountUsername)= :username", SESALabAccount::class.java)
            query.setParameter("username", username.lowercase())
            query.singleResult
        }
        catch (error: NoResultException)
        {
            throw IllegalAccountException("account with username #$username not found")
        }
    }
}