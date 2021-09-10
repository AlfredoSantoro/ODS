package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.repository.ReservationRepositoryDB2Impl
import com.unisa.sesalab.ods.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [OdsApplication::class])
abstract class BaseTest
{
    @Autowired
    protected lateinit var usersService: UsersService

    @Autowired
    protected lateinit var reservationRepositoryDB2Impl: ReservationRepositoryDB2Impl
}