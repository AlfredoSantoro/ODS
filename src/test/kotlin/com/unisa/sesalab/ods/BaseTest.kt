package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
abstract class BaseTest
{
    @Autowired
    protected lateinit var usersService: UsersService
}