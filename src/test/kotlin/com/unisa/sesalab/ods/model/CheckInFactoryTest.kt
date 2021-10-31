package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.BaseTest
import com.unisa.sesalab.ods.factory.CheckInFactory
import development.kit.user.AccountType
import development.kit.utils.PasswordManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class CheckInFactoryTest: BaseTest()
{
    @Test
    fun `Should create a new CheckIn model of the rdk library`()
    {
        val tag = TagNfc("test-tag", "test-value")
        val seat = StudySeat("", true, tag)
        val account = SESALabAccount("test-name", "test-surname", AccountType.USER, "test-email",
            "test-username", PasswordManager.encodePassword("lamiapass"))
        val res = Reservation("fake", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1),
        account, seat)
        val checkInEntity = CheckIn(res, OffsetDateTime.now())
        val checkInTransformed = CheckInFactory.createCheckIn(checkInEntity)
        Assertions.assertThat(checkInTransformed).isNotNull
        Assertions.assertThat(checkInTransformed.checkInId).isEqualTo(-1)
        Assertions.assertThat(checkInTransformed.isValid).isTrue
        Assertions.assertThat(checkInTransformed.reservation.id).isEqualTo(-1)
        Assertions.assertThat(checkInTransformed.owner.username).isEqualTo(res.account.username)
    }
}