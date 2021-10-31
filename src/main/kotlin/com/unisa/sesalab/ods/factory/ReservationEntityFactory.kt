package com.unisa.sesalab.ods.factory

import com.unisa.sesalab.ods.model.Reservation
import com.unisa.sesalab.ods.model.SESALabAccount
import com.unisa.sesalab.ods.model.StudySeat
import development.kit.reservation.BaseReservation

object ReservationEntityFactory
{
    fun createReservation(baseReservation: BaseReservation,
                          account: SESALabAccount,
                          studySeat: StudySeat,
                          name: String): Reservation
    {
        val id = if ( baseReservation.id == -1L)
        {
            null
        }
        else
        {
            baseReservation.id
        }
        return Reservation(name, baseReservation.start, baseReservation.end, account, studySeat, false, id)
    }

    fun createBaseReservation(reservation: Reservation): BaseReservation
    {
        return BaseReservation(reservation.start, reservation.end,
            AssetFactory.createAsset(reservation.studySeatReserved), AccountFactory.createAccount(reservation.account), reservation.id ?: -1)
    }
}