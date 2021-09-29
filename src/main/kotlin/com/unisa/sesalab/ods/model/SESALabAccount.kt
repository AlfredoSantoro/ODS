package com.unisa.sesalab.ods.model

import development.kit.user.Account
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import javax.persistence.*

@Entity(name = "ACCOUNT")
class SESALabAccount(
        @Column(name = "NAME", nullable = false)
        var accountName: String,
        @Column(name = "SURNAME", nullable = false)
        var accountSurname: String,
        @Column(name = "ACCOUNT_TYPE", nullable = false)
        @Enumerated(EnumType.STRING)
        var userType: AccountType,
        @Column(name = "EMAIL", nullable = false)
        var accountEmail: String,
        @Column(name = "USERNAME", nullable = false, unique = true)
        var accountUsername: String,
        @Column(name = "PASSWORD", nullable = false)
        var encodedPassword: String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long ? = null
): Account(accountName, accountSurname, accountEmail, accountUsername, encodedPassword, userType, id)
{
    constructor(createAccount: CreateAccount) : this(
        createAccount.name,
        createAccount.surname,
        createAccount.accountType,
        createAccount.email,
        createAccount.username,
        createAccount.password
    )

    constructor(account: Account): this(
        account.name,
        account.surname,
        account.accountType,
        account.email,
        account.username,
        account.password,
        account.accountId
    )

    override fun toString(): String
    {
        return "User(name='$name', surname='$surname', userType=$userType, email='$email', " +
                "username='$username', password='$encodedPassword', id=$id)"
    }

}