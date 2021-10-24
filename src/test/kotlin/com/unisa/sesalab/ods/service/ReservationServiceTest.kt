package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.TagNfcDTO
import development.kit.exception.IllegalReservationException
import development.kit.exception.ReservationOverlapsException
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
    fun `Should create a new reservation`()
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
        val reservation = this.reservationService.findAllReservationsOnGoingByUser(seSaLabAccount.username)
        Assertions.assertThat(reservation).isNotNull
        Assertions.assertThat(reservation!!.account.id).isEqualTo(seSaLabAccount.id)
        Assertions.assertThat(reservation.studySeatReserved.id).isEqualTo(seat.id)
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

    @Test
    fun `Should not create a reservation because it overlaps another one (case 5)`()
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
        val tagNFC2 = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag-2", "value-2"))
        Assertions.assertThat(tagNFC2).isNotNull
        Assertions.assertThat(tagNFC2.id).isNotNull
        Assertions.assertThat(tagNFC2.id).isNotEqualTo(-1)
        val seat2 = this.seatService.createSeat(SeatInsertDTO("test-seat-2", true, tagNFC2.id!!))
        Assertions.assertThat(seat2).isNotNull
        Assertions.assertThat(seat2!!.id).isNotNull
        Assertions.assertThat(seat2.id).isNotEqualTo(-1)

        val start = OffsetDateTime.now()
        val validReservation = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(validReservation)

        val reservationOverlap = ReservationInsertDTO("reservation-test", start.plusMinutes(20),
            Duration.ofHours(1), seSaLabAccount, seat2)
        assertThrows<ReservationOverlapsException> {
            this.reservationService.createReservation(reservationOverlap)
        }
    }
}