package com.unisa.sesalab.ods.model

import development.kit.user.Account
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import javax.persistence.*

@Entity(name = "ACCOUNT")
class SESALabAccount(
    var name: String,
    var surname: String,
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    var userType: AccountType,
    var email: String,
    @Column(name = "USERNAME", nullable = false, unique = true)
    var username: String,
    @Column(name = "PASSWORD", nullable = false)
    var encodedPassword: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
)
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