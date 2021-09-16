package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.enum.UserType
import org.apache.commons.codec.digest.DigestUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class UserServiceImplTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val user = UserInsertUpdateDTO(null,
                "nameTest", "surnameTest", UserType.ADMIN,
                "test@gmail.com", "usertest", "pass")
        val userSaved = this.userServiceImpl.signUpUser(user)
        Assertions.assertThat(userSaved).isNotNull
        Assertions.assertThat(userSaved!!.id).isNotEqualTo(-1)
    }

    @Test
    fun `Should delete a user`()
    {
        val user = UserInsertUpdateDTO(null,
                "nameTest", "surnameTest", UserType.ADMIN,
                "test@gmail.com", "usertest_2", "pass")
        val userSaved = this.userServiceImpl.signUpUser(user)
        Assertions.assertThat(userSaved).isNotNull
        Assertions.assertThat(userSaved!!.id).isNotEqualTo(-1)
        this.userServiceImpl.deleteAccount(userSaved.id!!)
        Assertions.assertThat(this.userServiceImpl.viewAccount(userSaved.id!!)).isNull()
    }

    @Test
    fun `Should update an entity`()
    {
        val user = UserInsertUpdateDTO(null,
                "nameTest", "surnameTest", UserType.ADMIN,
                "test@gmail.com", "usertest_3", "pass")
        val userSaved = this.userServiceImpl.signUpUser(user)
        Assertions.assertThat(userSaved).isNotNull
        Assertions.assertThat(userSaved!!.id).isNotEqualTo(-1)
        val userUpdated = this.userServiceImpl.updateAccount(UserInsertUpdateDTO(userSaved.id, userSaved.name, surname = "usertest_001",
                userSaved.userType, "newemail@test.it", "newusername_today", plainPassword = "newpass"))
        Assertions.assertThat(userUpdated).isNotNull
        Assertions.assertThat(userUpdated!!.surname).isEqualTo("usertest_001")
        Assertions.assertThat(userUpdated.email).isEqualTo("newemail@test.it")
        Assertions.assertThat(userUpdated.username).isEqualTo("newusername_today")
        Assertions.assertThat(userUpdated.encodedPassword).isEqualTo(DigestUtils.sha256Hex("newpass"))
    }

    @Test
    fun `Should find a user by username`()
    {
        val user = UserInsertUpdateDTO(null,
                "nameTest", "surnameTest", UserType.ADMIN,
                "test@gmail.com", "usertest_4", "pass")
        val userSaved = this.userServiceImpl.signUpUser(user)
        Assertions.assertThat(userSaved).isNotNull
        Assertions.assertThat(userSaved!!.id).isNotEqualTo(-1)
        Assertions.assertThat(this.userServiceImpl.findUserByUsername(userSaved.username)).isNotNull
    }
}
