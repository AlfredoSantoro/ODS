package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import com.unisa.sesalab.ods.model.Users
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersServiceTest: BaseTest()
{
    private val users = mutableListOf<Users>()

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
        this.users.add(userOnDb)
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

/*    @AfterAll
    fun deleteMockUsers()
    {
        this.users.forEach {
            this.usersService.deleteById(it.id)
        }
    }*/
}