package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.Reservation
import development.kit.exception.IllegalReservationException
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import development.kit.utils.PasswordManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Duration
import java.time.OffsetDateTime

class ReservationServiceTest: BaseTest()
{
    @Test
    fun `Should get all reservations onGoing`()
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
        Assertions.assertThat(this.reservationService.findAllReservationsOnGoing().size).isEqualTo(1)
    }

    @Test
    fun `Should create a new reservation (case 1)`()
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
        val start = OffsetDateTime.now()
        val reservationInsertDTO = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
       this.reservationService.createReservation(reservationInsertDTO)
       val res = this.reservationService.findAllReservationsOnGoing().filter { it.account.id == seSaLabAccount.id }
       Assertions.assertThat(res).isNotEmpty
       res.forEach {
           Assertions.assertThat(it.seatReserved.id).isEqualTo(seat.id)
           Assertions.assertThat(it.account.id).isEqualTo(seSaLabAccount.id)
           Assertions.assertThat(it.name).isEqualTo("reservation-test")
           Assertions.assertThat(it.start).isEqualTo(start)
           Assertions.assertThat(it.end).isEqualTo(start.plusHours(1))
       }
    }

    @Test
    fun `Should not create a reservation because it overlaps another one (case 1)`()
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
        val start = OffsetDateTime.now()
        val validReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(validReservation)

        val wrongReservation = ReservationInsertDTO("reservation-test", start.plusMinutes(10),
            Duration.ofHours(1), seSaLabAccount, seat)

        val ex = assertThrows<IllegalReservationException>("Asset #${seat.name} not available") {
            this.reservationService.createReservation(wrongReservation)
        }
        Assertions.assertThat("Asset #${seat.name} not available").isEqualTo(ex.msg)
    }

    @Test
    fun `Should not create a reservation because it overlaps another one (case 2)`()
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
        val start = OffsetDateTime.now()
        val validReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(validReservation)

        val wrongReservation = ReservationInsertDTO("reservation-test", start.minusMinutes(10),
            Duration.ofMinutes(20), seSaLabAccount, seat)
        val ex = assertThrows<IllegalReservationException>("Asset #${seat.name} not available") {
            this.reservationService.createReservation(wrongReservation)
        }
        Assertions.assertThat("Asset #${seat.name} not available").isEqualTo(ex.msg)
    }

    @Test
    fun `Should not create a reservation because it overlaps another one (case 3)`()
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
        val start = OffsetDateTime.now()
        val validReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(validReservation)

        val wrongReservation = ReservationInsertDTO("reservation-test", start.minusMinutes(10),
            Duration.ofHours(2), seSaLabAccount, seat)
        val ex = assertThrows<IllegalReservationException>("Asset #${seat.name} not available") {
            this.reservationService.createReservation(wrongReservation)
        }
        Assertions.assertThat("Asset #${seat.name} not available").isEqualTo(ex.msg)
    }

    @Test
    fun `Should not create a reservation because it overlaps another one (case 4)`()
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
        val start = OffsetDateTime.now()
        val validReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(validReservation)

        val wrongReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        val ex = assertThrows<IllegalReservationException>("Asset #${seat.name} not available") {
            this.reservationService.createReservation(wrongReservation)
        }
        Assertions.assertThat("Asset #${seat.name} not available").isEqualTo(ex.msg)
    }

    @Test
    fun `Should create a reservation because it overlaps another one (case 2)`()
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
        val start = OffsetDateTime.now()
        val firstValidReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(firstValidReservation)

        val secondValidReservation = ReservationInsertDTO("reservation-test", start.minusHours(1),
            Duration.ofHours(1), seSaLabAccount, seat)

        this.reservationService.createReservation(secondValidReservation)
    }

    @Test
    fun `Should create a reservation because it overlaps another one (case 3)`()
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
        val start = OffsetDateTime.now()
        val firstValidReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(firstValidReservation)

        val secondValidReservation = ReservationInsertDTO("reservation-test", start.plusHours(2),
            Duration.ofHours(1), seSaLabAccount, seat)

        this.reservationService.createReservation(secondValidReservation)
    }
}