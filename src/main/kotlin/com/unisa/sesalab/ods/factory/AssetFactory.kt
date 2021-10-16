package com.unisa.sesalab.ods.factory

import com.unisa.sesalab.ods.model.StudySeat
import development.kit.asset.Asset
import development.kit.asset.Seat

object AssetFactory
{
    fun createAsset(studySeat: StudySeat): Asset
    {
        val asset = Asset(studySeat.name, studySeat.canBeBooked)
        asset.assetId = studySeat.id
        return asset
    }

    fun createSeat(studySeat: StudySeat): Seat
    {
        return Seat(studySeat.name, studySeat.canBeBooked)
    }
}