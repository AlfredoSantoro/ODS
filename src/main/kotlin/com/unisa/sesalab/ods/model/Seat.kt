package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.enum.AssetType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToOne

@Entity(name = "SEAT")
data class Seat(
        var seatName: String,
        var seatCanBeBooked: Boolean,
        @OneToOne(fetch = FetchType.LAZY)
        var tagNfc: TagNfc
): Asset(seatName, seatCanBeBooked, AssetType.SEAT)