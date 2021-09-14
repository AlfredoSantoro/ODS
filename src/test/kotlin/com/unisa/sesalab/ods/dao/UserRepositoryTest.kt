package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserInsertUpdateDTO
import com.unisa.sesalab.ods.enum.UserType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class UserRepositoryTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val user = UserInsertUpdateDTO(null, "Mario", "Rossi", UserType.USER, "mariorossi@test.it", "mariored", "lamiapass")
        val userOnDB = this.userRepositoryImpl.insertUser(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
    }

    @Test
    fun `Should update an user`()
    {
        val user = UserInsertUpdateDTO(null,"Mario", "Rossi", UserType.USER, "mariorossi@test.it", "mariored2", "lamiapass")
        val userOnDB = this.userRepositoryImpl.insertUser(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
        val userToUP = this.userRepositoryImpl.findUserById(userOnDB.id!!)
        userToUP!!.username = "newusername"
        val userDtoToUpdate = UserInsertUpdateDTO(userToUP.id, userToUP.name, userToUP.surname, userToUP.userType, userToUP.email, userToUP.username, user.plainPassword)
        Assertions.assertThat(this.userRepositoryImpl.updateUser(userDtoToUpdate)!!.username).isEqualTo("newusername")
    }

    @Test
    fun `Should find a user by username`()
    {
        val user = UserInsertUpdateDTO(null,"Mario3", "Rossi3", UserType.USER, "mariorossi@test.it", "mariored3", "lamiapass")
        val userOnDB = this.userRepositoryImpl.insertUser(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
        Assertions.assertThat(this.userRepositoryImpl.findByUsernameIgnoreCase(userOnDB.username)!!.username).isEqualTo(userOnDB.username)
    }

    @Test
    fun `Should find a user by id`()
    {
        val user = UserInsertUpdateDTO(null,"Mario4", "Rossi4", UserType.USER, "mariorossi@test.it", "mariored4", "lamiapass")
        val userOnDB = this.userRepositoryImpl.insertUser(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
        Assertions.assertThat(this.userRepositoryImpl.findUserById(userOnDB.id!!)!!.id).isEqualTo(userOnDB.id)
    }
}