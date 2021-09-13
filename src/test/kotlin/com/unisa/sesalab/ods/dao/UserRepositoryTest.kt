package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import com.unisa.sesalab.ods.model.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class UserRepositoryTest: BaseTest()
{
    @Test
    fun `Should save a new User`()
    {
        val user = User(UserDTO("Mario", "Rossi", UserType.USER, "mariorossi@test.it", "mariored", "lamiapass"))
        val userOnDB = this.userRepositoryImpl.saveNewEntity(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
    }

    @Test
    fun `Should update an user`()
    {
        val user = User(UserDTO("Mario", "Rossi", UserType.USER, "mariorossi@test.it", "mariored2", "lamiapass"))
        val userOnDB = this.userRepositoryImpl.saveNewEntity(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
        val userToUP = this.userRepositoryImpl.findById(userOnDB.id!!)
        userToUP.username = "newusername"
        Assertions.assertThat(this.userRepositoryImpl.update(userToUP).username).isEqualTo("newusername")
    }

    @Test
    fun `Should find a user by username`()
    {
        val user = User(UserDTO("Mario3", "Rossi3", UserType.USER, "mariorossi@test.it", "mariored3", "lamiapass"))
        val userOnDB = this.userRepositoryImpl.saveNewEntity(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
        Assertions.assertThat(this.userRepositoryImpl.findByUsernameIgnoreCase(userOnDB.username)!!.username).isEqualTo(userOnDB.username)
    }

    @Test
    fun `Should find a user by id`()
    {
        val user = User(UserDTO("Mario4", "Rossi4", UserType.USER, "mariorossi@test.it", "mariored4", "lamiapass"))
        val userOnDB = this.userRepositoryImpl.saveNewEntity(user)
        Assertions.assertThat(userOnDB).isNotNull
        Assertions.assertThat(userOnDB.id).isNotNull
        Assertions.assertThat(userOnDB.id).isNotEqualTo(-1)
        Assertions.assertThat(this.userRepositoryImpl.findById(userOnDB.id!!).id).isEqualTo(userOnDB.id)
    }
}