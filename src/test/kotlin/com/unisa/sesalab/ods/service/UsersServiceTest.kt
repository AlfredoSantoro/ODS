package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import com.unisa.sesalab.ods.model.Users
import org.junit.jupiter.api.Test


class UsersServiceTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val userToSave = UserDTO(
                "NameTest",
                "SurnameTest",
                UserType.ADMIN,
                "test@test.it",
                "username_test",
                "password_test",
                null
        )

        this.usersService.saveUser(Users(userToSave))
    }
}