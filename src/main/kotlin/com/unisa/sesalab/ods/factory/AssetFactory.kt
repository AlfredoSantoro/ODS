package com.unisa.sesalab.ods.factory

import com.unisa.sesalab.ods.model.Seat
import development.kit.asset.Asset

object AssetFactory
{
    fun createAsset(seat: Seat): Asset
    {
        val asset = Asset(seat.name, seat.canBeBooked)
        asset.assetId = seat.id
        return asset
    }
}