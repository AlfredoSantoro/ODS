package com.unisa.sesalab.ods.exception

data class AccessAuthorizationConstraintsException(val msg: String): RuntimeException(msg) {}