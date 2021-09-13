package com.unisa.sesalab.ods.exception

data class ReservationConstraintsException(val msg: String): RuntimeException(msg) {}