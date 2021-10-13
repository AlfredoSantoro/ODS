package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.BaseTest
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SESALabAccountRepositoryTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored1", "lamiapass", AccountType.USER)
        val accountOnDB = this.userRepositoryImpl.saveAccount(user)
        Assertions.assertThat(accountOnDB).isNotNull
        Assertions.assertThat(accountOnDB).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotEqualTo(-1)
    }

    @Test
    fun `Should update an user`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored2", "lamiapass", AccountType.USER)
        val accountOnDB = this.userRepositoryImpl.saveAccount(user)
        Assertions.assertThat(accountOnDB).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotEqualTo(-1)
        val userToUP = this.userRepositoryImpl.findAccountById(accountOnDB.accountId!!)!!
        userToUP.username = "newusername"
        Assertions.assertThat(this.userRepositoryImpl.updateAccount(userToUP).username).isEqualTo("newusername")
    }

    @Test
    fun `Should find a user by username`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored3", "lamiapass", AccountType.USER)
        val accountOnDB = this.userRepositoryImpl.saveAccount(user)
        Assertions.assertThat(accountOnDB).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotEqualTo(-1)
        Assertions.assertThat(this.userRepositoryImpl.findAccountByUsername(accountOnDB.username)!!.username)
            .isEqualTo(accountOnDB.username)
    }

    @Test
    fun `Should find a user by id`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored4", "lamiapass", AccountType.USER)
        val accountOnDB = this.userRepositoryImpl.saveAccount(user)
        Assertions.assertThat(accountOnDB).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotNull
        Assertions.assertThat(accountOnDB.accountId).isNotEqualTo(-1)
        Assertions.assertThat(this.userRepositoryImpl.findAccountById(accountOnDB.accountId!!)!!.username)
            .isEqualTo(accountOnDB.username)
    }
}