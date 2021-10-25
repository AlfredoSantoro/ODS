package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.Reservation
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import development.kit.utils.PasswordManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ReservationRepositorTest: BaseTest()
{
    @Test
    fun `Should get the reservation on going of an user`()
    {
        val account = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored4", "lamiapass", AccountType.USER)
        val seSaLabAccount = this.userServiceImpl.signUpUser(account)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("lamiapass"))
        val tagNFC = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag", "value"))
        Assertions.assertThat(tagNFC).isNotNull
        Assertions.assertThat(tagNFC.id).isNotNull
        Assertions.assertThat(tagNFC.id).isNotEqualTo(-1)
        val seat = this.seatService.createSeat(SeatInsertDTO("test-seat", true, tagNFC.id!!))
        Assertions.assertThat(seat).isNotNull
        Assertions.assertThat(seat!!.id).isNotNull
        Assertions.assertThat(seat.id).isNotEqualTo(-1)
        val newReservation = Reservation("testReservation", OffsetDateTime.now(),
            OffsetDateTime.now().plusHours(1), seSaLabAccount, seat)
        this.reservationRepo.insertReservation(newReservation)
        val reservation = this.reservationService.findReservationOnGoingByUser(seSaLabAccount.username)
        Assertions.assertThat(reservation).isNotNull
        Assertions.assertThat(reservation!!.account.id).isEqualTo(seSaLabAccount.id)
        Assertions.assertThat(reservation.studySeatReserved.id).isEqualTo(seat.id)
    }
}