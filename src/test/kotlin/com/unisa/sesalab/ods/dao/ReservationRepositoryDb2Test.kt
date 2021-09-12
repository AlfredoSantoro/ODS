package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.UserDTO
import com.unisa.sesalab.ods.enum.UserType
import com.unisa.sesalab.ods.model.Reservations
import com.unisa.sesalab.ods.model.Seat
import com.unisa.sesalab.ods.model.TagNfc
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ReservationRepositoryDb2Test: BaseTest()
{
    @Test
    fun `Should save a new Reservation`()
    {
        val userDTO = UserDTO(
                "NameTest",
                "SurnameTest",
                UserType.ADMIN,
                "test@test.it",
                "username_test",
                "password_test"
        )
        val userID = this.usersService.signUpUser(userDTO)
        Assertions.assertThat(userID).isNotNull
        val userOnDb = this.usersService.findById(userID!!)
        Assertions.assertThat(userOnDb).isNotNull
        val tagNFCId = this.tagNFCRepositoryDB2Impl.save(TagNfc("test-tag", "kljhdslaubheuy/?923775"))
        val tagNFC = this.tagNFCRepositoryDB2Impl.findById(tagNFCId)
        val seatID = this.seatRepositoryDB2Impl.save(Seat("postazione 1", true, tagNFC))
        val seat = this.seatRepositoryDB2Impl.findById(seatID)
        val reservation =
                Reservations("test reservation",
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        userOnDb!!,
                        seat
                )
        val resSaved = this.reservationRepositoryDB2Impl.findById(this.reservationRepositoryDB2Impl.save(reservation))
        Assertions.assertThat(resSaved).isNotNull
    }
}