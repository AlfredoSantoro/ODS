package com.unisa.sesalab.ods.dto

import com.unisa.sesalab.ods.model.SESALabAccount
import com.unisa.sesalab.ods.model.StudySeat
import java.time.Duration
import java.time.OffsetDateTime

data class ReservationInsertDTO(
        val name: String,
        val start: OffsetDateTime,
        val reservationDuration: Duration,
        val account: SESALabAccount,
        val studySeat: StudySeat
)