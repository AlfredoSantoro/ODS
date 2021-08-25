package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UsersServiceTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val userDTO = UserDTO(
                "NameTest",
                "SurnameTest",
                UserType.ADMIN,
                "test@test.it",
                "username_test",
                "password_test",
                null
        )
        val userID = this.usersService.saveUser(userDTO)
        val userOnDb = this.usersService.findById(userID)
        Assertions.assertThat(userOnDb).isNotNull
        Assertions.assertThat(userOnDb.id).isNotEqualTo(-1)
        Assertions.assertThat(userOnDb.name).isEqualTo(userDTO.name)
        Assertions.assertThat(userOnDb.surname).isEqualTo(userDTO.surname)
        Assertions.assertThat(userOnDb.userType).isEqualTo(userDTO.userType)
        Assertions.assertThat(userOnDb.email).isEqualTo(userDTO.email)
        Assertions.assertThat(userOnDb.username).isEqualTo(userDTO.username)
        Assertions.assertThat(userOnDb.deleted).isEqualTo(false)
        Assertions.assertThat(userOnDb.password).isEqualTo(userDTO.password)
        Assertions.assertThat(userOnDb.validUntil).isEqualTo(LocalDate.now().plusYears(100))
    }

    @Test
    fun `Should perform a soft delete`()
    {
        val userDTO = UserDTO(
                "NameTest",
                "SurnameTest",
                UserType.ADMIN,
                "test@test.it",
                "username_test2",
                "password_test",
                null
        )
        val userID = this.usersService.saveUser(userDTO)
        Assertions.assertThat(userID).isNotEqualTo(-1)
        this.usersService.deleteById(userID)
        val userOnDB = this.usersService.findById(userID)
        Assertions.assertThat(userOnDB.deleted).isTrue
    }

    @Test
    fun `Should update an entity`()
    {
        val userDTO = UserDTO(
                "NameTest",
                "SurnameTest",
                UserType.ADMIN,
                "test@test.it",
                "username_test3",
                "password_test",
                null
        )
        val userID = this.usersService.saveUser(userDTO)
        val userOnDb = this.usersService.findById(userID)
        Assertions.assertThat(userOnDb).isNotNull
        Assertions.assertThat(userOnDb.id).isNotEqualTo(-1)
        Assertions.assertThat(userOnDb.name).isEqualTo(userDTO.name)
        Assertions.assertThat(userOnDb.surname).isEqualTo(userDTO.surname)
        Assertions.assertThat(userOnDb.userType).isEqualTo(userDTO.userType)
        Assertions.assertThat(userOnDb.email).isEqualTo(userDTO.email)
        Assertions.assertThat(userOnDb.username).isEqualTo(userDTO.username)
        Assertions.assertThat(userOnDb.deleted).isEqualTo(false)
        Assertions.assertThat(userOnDb.password).isEqualTo(userDTO.password)
        Assertions.assertThat(userOnDb.validUntil).isEqualTo(LocalDate.now().plusYears(100))
        val newUser = UserDTO(userOnDb)
        newUser.username = "new-username"
        newUser.password = "new-password"
        this.usersService.update(userID, newUser)
        val newUserOnDB = this.usersService.findById(userID)
        Assertions.assertThat(newUserOnDB.username).isEqualTo("new-username")
        Assertions.assertThat(newUserOnDB.password).isEqualTo("new-password")
    }

    @Test
    fun `Should find a user by username`()
    {
        val userDTO = UserDTO(
                "NameTest",
                "SurnameTest",
                UserType.ADMIN,
                "test@test.it",
                "username_test3",
                "password_test",
                null
        )
        val userID = this.usersService.saveUser(userDTO)
        Assertions.assertThat(userID).isNotNull
        Assertions.assertThat(userID).isNotEqualTo(-1)
        val userByUsername = this.usersService.findByUsername(userDTO.username)
        Assertions.assertThat(userByUsername).isNotNull
        Assertions.assertThat(userByUsername!!.id).isEqualTo(userID)
        Assertions.assertThat(userByUsername.username).isEqualTo(userDTO.username)
    }
}