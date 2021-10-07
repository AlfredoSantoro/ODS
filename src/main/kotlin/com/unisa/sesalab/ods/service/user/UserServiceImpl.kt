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

    /**
     * Registra un nuovo account codificando la password con l'algoritmo SHA-256.
     * @param createAccount rappresenta i dati del nuovo account.
     * @return @SESALabAccount è l'entità resa persistente nella rispettiva tabella (ACCOUNT).
     */
    override fun signUpUser(createAccount: CreateAccount): SESALabAccount
    {
        // 1. codifica password
        createAccount.password = PasswordManager.encodePassword(createAccount.password)

        // 2. registra account se e soltanto se non esso non esiste già
        val account = this.accountManagerStorage.signUpANewAccount(createAccount)

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

    override fun findUserByUsername(username: String): SESALabAccount? { return this.userRepository.findByUsernameIgnoreCase(username) }

    override fun updateAccount(updateAccount: UpdateAccount): SESALabAccount
    {
        updateAccount.password = PasswordManager.encodePassword(updateAccount.password)
        val accountUpdated = this.accountManagerStorage.updateAnExistingAccountAndStoreIt(updateAccount)
        return SESALabAccount(accountUpdated)
    }
}
