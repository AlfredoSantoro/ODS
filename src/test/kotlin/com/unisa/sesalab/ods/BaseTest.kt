package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.repository.reservations.ReservationRepository
import com.unisa.sesalab.ods.repository.users.UserRepository
import com.unisa.sesalab.ods.service.reservation.ReservationService
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.tagnfc.TagNFCService
import com.unisa.sesalab.ods.service.user.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
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
    protected lateinit var userServiceImpl: UserServiceImpl
}