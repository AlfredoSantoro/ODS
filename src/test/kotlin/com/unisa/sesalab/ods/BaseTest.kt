package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.repository.reservations.ReservationRepositoryImpl
import com.unisa.sesalab.ods.repository.users.UserRepository
import com.unisa.sesalab.ods.service.user.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest(classes = [OdsApplication::class])
@Transactional
abstract class BaseTest
{
    @Autowired
    protected lateinit var reservationRepositoryImpl: ReservationRepositoryImpl

    @Autowired
    protected lateinit var userRepositoryImpl: UserRepository

    @Autowired
    protected lateinit var userServiceImpl: UserServiceImpl
}