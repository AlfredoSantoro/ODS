package com.unisa.sesalab.ods.service.asset

import com.unisa.sesalab.ods.model.Asset

interface AssetService
{
    fun findAssetById(id: Long): Asset?
}