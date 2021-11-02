package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.dto.ReservationInsertDTO
import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.factory.CheckInFactory
import com.unisa.sesalab.ods.model.CheckIn
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import development.kit.utils.PasswordManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.OffsetDateTime

class CheckInRuleServiceTest: BaseTest()
{
    @Test
    fun `Should say you that the initial check-in was done in time`()
    {
        val account = CreateAccount("test-name", "test-surname","test@test.it" ,
            "test", "test_pass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(account)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("test_pass"))
        val tagNFC = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag", "UUID-test_tag"))
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
        val reservation = this.reservationService.findReservationOnGoingByUser(seSaLabAccount.username)
        val isValid = this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(CheckIn(reservation!!, OffsetDateTime.now())))
        Assertions.assertThat(isValid).isTrue
    }

    @Test
    fun `Should say you that the initial check-in was not done in time`()
    {
        val checkInFrequencySetting = this.settingService.findByName(this.checkInFrequencySettingName)
        Assertions.assertThat(checkInFrequencySetting).isNotNull
        val checkInFrequency = checkInFrequencySetting!!.value
        val account = CreateAccount("test-name", "test-surname","test@test.it" ,
            "test", "test_pass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(account)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("test_pass"))
        val tagNFC = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag", "UUID-test_tag"))
        Assertions.assertThat(tagNFC).isNotNull
        Assertions.assertThat(tagNFC.id).isNotNull
        Assertions.assertThat(tagNFC.id).isNotEqualTo(-1)
        val seat = this.seatService.createSeat(SeatInsertDTO("test-seat", true, tagNFC.id!!))
        Assertions.assertThat(seat).isNotNull
        Assertions.assertThat(seat!!.id).isNotNull
        Assertions.assertThat(seat.id).isNotEqualTo(-1)
        val start = OffsetDateTime.now().minusMinutes(checkInFrequency.toLong())
        val reservationInsertDTO = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(reservationInsertDTO)
        val reservation = this.reservationService.findReservationOnGoingByUser(seSaLabAccount.username)
        val isValid = this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(CheckIn(reservation!!, OffsetDateTime.now())))
        Assertions.assertThat(isValid).isFalse
    }

    @Test
    fun `Should say you that the check-in frequency was not done in time`()
    {
        val checkInFrequencySetting = this.settingService.findByName(this.checkInFrequencySettingName)
        Assertions.assertThat(checkInFrequencySetting).isNotNull
        val checkInFrequency = checkInFrequencySetting!!.value
        val account = CreateAccount("test-name", "test-surname","test@test.it" ,
            "test", "test_pass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(account)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("test_pass"))
        val tagNFC = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag", "UUID-test_tag"))
        Assertions.assertThat(tagNFC).isNotNull
        Assertions.assertThat(tagNFC.id).isNotNull
        Assertions.assertThat(tagNFC.id).isNotEqualTo(-1)
        val seat = this.seatService.createSeat(SeatInsertDTO("test-seat", true, tagNFC.id!!))
        Assertions.assertThat(seat).isNotNull
        Assertions.assertThat(seat!!.id).isNotNull
        Assertions.assertThat(seat.id).isNotEqualTo(-1)
        val start = OffsetDateTime.now().minusMinutes(checkInFrequency.toLong()+15)
        val reservationInsertDTO = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(reservationInsertDTO)
        val reservation = this.reservationService.findReservationOnGoingByUser(seSaLabAccount.username)
        val firstCheckIn = CheckIn(reservation!!, start.plusMinutes(1))
        val isValid = this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(firstCheckIn))
        Assertions.assertThat(isValid).isTrue
        firstCheckIn.isValid = isValid
        this.checkInService.saveCheckIn(firstCheckIn)
        val secondCheckIn = CheckIn(reservation, OffsetDateTime.now())
        Assertions.assertThat(this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(secondCheckIn))).isFalse
    }

    @Test
    fun `Should say you that the check-in frequency was done in time`()
    {
        val checkInFrequencySetting = this.settingService.findByName(this.checkInFrequencySettingName)
        Assertions.assertThat(checkInFrequencySetting).isNotNull
        val checkInFrequency = checkInFrequencySetting!!.value
        val account = CreateAccount("test-name", "test-surname","test@test.it" ,
            "test", "test_pass", AccountType.USER)
        val seSaLabAccount = this.userService.signUpUser(account)
        Assertions.assertThat(seSaLabAccount).isNotNull
        Assertions.assertThat(seSaLabAccount.id).isNotEqualTo(-1)
        Assertions.assertThat(seSaLabAccount.encodedPassword).isEqualTo(PasswordManager.encodePassword("test_pass"))
        val tagNFC = this.tagNFCService.createTagNFC(TagNfcDTO("test-tag", "UUID-test_tag"))
        Assertions.assertThat(tagNFC).isNotNull
        Assertions.assertThat(tagNFC.id).isNotNull
        Assertions.assertThat(tagNFC.id).isNotEqualTo(-1)
        val seat = this.seatService.createSeat(SeatInsertDTO("test-seat", true, tagNFC.id!!))
        Assertions.assertThat(seat).isNotNull
        Assertions.assertThat(seat!!.id).isNotNull
        Assertions.assertThat(seat.id).isNotEqualTo(-1)
        val start = OffsetDateTime.now().minusMinutes(checkInFrequency.toLong()+15)
        val reservationInsertDTO = ReservationInsertDTO("reservation-test", start,
            Duration.ofHours(1), seSaLabAccount, seat)
        this.reservationService.createReservation(reservationInsertDTO)
        val reservation = this.reservationService.findReservationOnGoingByUser(seSaLabAccount.username)
        val firstCheckIn = CheckIn(reservation!!, start.plusMinutes(1))
        val isValid = this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(firstCheckIn))
        Assertions.assertThat(isValid).isTrue
        firstCheckIn.isValid = isValid
        this.checkInService.saveCheckIn(firstCheckIn)
        val secondCheckIn = CheckIn(reservation, firstCheckIn.time.plusMinutes(10))
        Assertions.assertThat(this.checkInRuleService.isInTime(CheckInFactory.createCheckIn(secondCheckIn))).isTrue
    }
}