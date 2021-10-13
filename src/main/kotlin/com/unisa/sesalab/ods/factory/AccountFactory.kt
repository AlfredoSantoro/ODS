package com.unisa.sesalab.ods.factory

import com.unisa.sesalab.ods.model.SESALabAccount
import development.kit.user.Account
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AccountFactory
{
    private val logger: Logger = LoggerFactory.getLogger(AccountFactory::class.java)

    fun createAccount(SESALabAccount: SESALabAccount): Account
    {
        this.logger.info("### creating new account from SESALabAccount")
        val account = Account(SESALabAccount.name,
            SESALabAccount.surname,
            SESALabAccount.email,
            SESALabAccount.username,
            SESALabAccount.encodedPassword,
            SESALabAccount.userType)
        account.accountId = SESALabAccount.id
        return account
    }
}