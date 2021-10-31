package com.unisa.sesalab.ods.factory

import com.unisa.sesalab.ods.model.CheckIn

object CheckInFactory
{
    fun createCheckIn(checkInEntity: CheckIn): development.kit.checkin.CheckIn
    {
        val res = ReservationEntityFactory.createBaseReservation(checkInEntity.reservation)
        val account = AccountFactory.createAccount(checkInEntity.reservation.account)
        return development.kit.checkin.CheckIn(res, checkInEntity.time, account, checkInEntity.id ?: -1)
    }
}