package com.unisa.sesalab.ods.service

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

class ReservationServiceTest: BaseTest()
{
    @Test
    fun `Should get all reservations onGoing`()
    {
        val user = CreateAccount("Mario", "Rossi","mariorossi@test.it" ,
            "mariored4", "lamiapass", AccountType.USER)
        val seSaLabAccount = this.userServiceImpl.signUpUser(user)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("lamiapass"))
        val tagNFC = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag", "value"))
        val seat = this.seatService.createSeat(SeatInsertDTO("test-seat", true, tagNFC.id!!))!!
        val newReservation = Reservation("testReservation", OffsetDateTime.now(),
        OffsetDateTime.now().plusHours(1), seSaLabAccount, seat)
        this.reservationRepo.insertReservation(newReservation)
        Assertions.assertThat(this.reservationService.findAllReservationsOnGoing().size).isEqualTo(1)
    }
}