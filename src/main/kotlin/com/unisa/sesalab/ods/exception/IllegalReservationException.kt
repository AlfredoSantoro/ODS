package com.unisa.sesalab.ods.exception

data class IllegalReservationException(val msg: String): RuntimeException(msg) {}