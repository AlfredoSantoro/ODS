package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.enum.UserType
import java.time.LocalDate

interface User
{
    var name: String
    var surname: String
    var userType: UserType
    var email: String
    var username: String
    var password: String
    var validUntil: LocalDate?
}