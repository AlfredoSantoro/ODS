package com.unisa.sesalab.ods.service

import com.unisa.sesalab.ods.model.Asset
import com.unisa.sesalab.ods.repository.asset.AssetRepository
import org.springframework.stereotype.Repository

@Repository
class AssetServiceImpl(private val assetRepository: AssetRepository): AssetService
{
    override fun findAssetById(id: Long): Asset?
    {
        return this.assetRepository.findAssetById(id)
    }
}