package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import development.kit.user.UpdateAccount
import development.kit.utils.PasswordManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SESALabAccountServiceImplTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored4", "lamiapass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(user)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("lamiapass"))
    }

    @Test
    fun `Should delete a user`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored3", "lamiapass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(user)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("lamiapass"))
        this.userService.deleteAccount(seSaLabAccount.id!!)
        Assertions.assertThat(this.userService.viewAccount(seSaLabAccount.id!!)).isNull()
    }

    @Test
    fun `Should update an entity`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored2", "lamiapass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(user)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("lamiapass"))

        val userUpdated = this.userService.updateAccount(
            UpdateAccount(seSaLabAccount.id!!, seSaLabAccount.name, surname = "usertest_001",
                "newemail@test.it","newusername_today", password = "newpass", seSaLabAccount.userType)
        )
        Assertions.assertThat(userUpdated).isNotNull
        Assertions.assertThat(userUpdated.surname).isEqualTo("usertest_001")
        Assertions.assertThat(userUpdated.email).isEqualTo("newemail@test.it")
        Assertions.assertThat(userUpdated.username).isEqualTo("newusername_today")
        Assertions.assertThat(userUpdated.encodedPassword).isEqualTo(PasswordManager.encodePassword("newpass"))
    }

    @Test
    fun `Should find a user by username`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored", "lamiapass", AccountType.ADMIN)
        val seSaLabAccount = this.userService.signUpUser(user)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("lamiapass"))
        val accountFounded = this.userService.findUserByUsername(seSaLabAccount.username)
        Assertions.assertThat(accountFounded).isNotNull
        Assertions.assertThat(accountFounded!!.username).isEqualTo(user.username)
    }
}
