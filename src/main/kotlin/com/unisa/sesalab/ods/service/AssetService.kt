package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.model.Asset

interface AssetService
{
    fun findAssetById(id: Long): Asset?
}