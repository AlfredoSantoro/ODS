package com.unisa.sesalab.ods.service.user

import com.unisa.sesalab.ods.model.SESALabAccount
import com.unisa.sesalab.ods.repository.users.UserRepository
import development.kit.user.AccountManagerStorage
import development.kit.user.CreateAccount
import development.kit.user.UpdateAccount
import development.kit.utils.PasswordManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        private val userRepository: UserRepository
): UserService
{
    private val accountManagerStorage = AccountManagerStorage(this.userRepository)
    private val logger: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun signUpUser(createAccount: CreateAccount): SESALabAccount
    {
        createAccount.password = PasswordManager.encodePassword(createAccount.password)
        val account = this.accountManagerStorage.signUpAccountIfItDoesNotAlreadyExist(createAccount)
        return SESALabAccount(account)
    }

    override fun deleteAccount(userId: Long)
    {
        this.userRepository.deleteUser(userId)
        this.logger.info("### account $userId deleted successfully")
    }

    override fun viewAccount(userId: Long): SESALabAccount?
    {
        val account = this.userRepository.findAccountById(userId)
        return if ( account == null ) null else SESALabAccount(account)
    }

    fun findUserByUsername(username: String): SESALabAccount? { return this.userRepository.findByUsernameIgnoreCase(username) }

    override fun updateAccount(updateAccount: UpdateAccount): SESALabAccount
    {
        updateAccount.password = PasswordManager.encodePassword(updateAccount.password)
        val accountUpdated = this.accountManagerStorage.findAccountUpdateAndStoreIt(updateAccount)
        return SESALabAccount(accountUpdated)
    }
}
