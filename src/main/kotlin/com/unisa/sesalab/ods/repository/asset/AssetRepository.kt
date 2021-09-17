package com.unisa.sesalab.ods.repository.asset

import com.unisa.sesalab.ods.model.Asset

interface AssetRepository
{
    fun findAssetById(id: Long): Asset?
}