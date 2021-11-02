package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import com.unisa.sesalab.ods.repository.users.UserRepository
import com.unisa.sesalab.ods.service.checkin.CheckInRuleService
import com.unisa.sesalab.ods.service.checkin.CheckInService
import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.setting.SettingService
import com.unisa.sesalab.ods.service.tagnfc.TagNFCService
import com.unisa.sesalab.ods.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest(classes = [OdsApplication::class])
@Transactional
abstract class BaseTest
{
    @Autowired
    protected lateinit var reservationService: ReservationService

    @Autowired
    protected lateinit var tagNFCService: TagNFCService

    @Autowired
    protected lateinit var seatService: SeatService

    @Autowired
    protected lateinit var reservationRepo: ReservationRepository

    @Autowired
    protected lateinit var userRepositoryImpl: UserRepository

    @Autowired
    protected lateinit var userService: UserService

    @Value("\${settings.default-names.checkInFrequency}")
    protected lateinit var checkInFrequencySettingName: String

    @Autowired
    protected lateinit var checkInRuleService: CheckInRuleService

    @Autowired
    protected lateinit var settingService: SettingService

    @Autowired
    protected lateinit var checkInService: CheckInService
}