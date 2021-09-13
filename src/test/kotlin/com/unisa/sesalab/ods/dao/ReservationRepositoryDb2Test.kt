package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.BaseTest

class ReservationRepositoryDb2Test: BaseTest()
{
/*    @Test
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
        val tagNFCId = this.tagNFCRepositoryDB2Impl.saveNewEntity(TagNfc("test-tag", "kljhdslaubheuy/?923775"))
        val tagNFC = this.tagNFCRepositoryDB2Impl.findById(tagNFCId)
        val seatID = this.seatRepositoryDB2Impl.saveNewEntity(Seat("postazione 1", true, tagNFC))
        val seat = this.seatRepositoryDB2Impl.findById(seatID)
        val reservation =
                Reservation("test reservation",
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        userOnDb!!,
                        seat
                )
        val resSaved = this.reservationRepositoryDB2Impl.findById(this.reservationRepositoryDB2Impl.saveNewEntity(reservation))
        Assertions.assertThat(resSaved).isNotNull
    }*/
}