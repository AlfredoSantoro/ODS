package com.unisa.sesalab.ods.repository.asset

import com.unisa.sesalab.ods.model.Asset
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.springframework.stereotype.Repository

@Repository
class AssetRepositoryImpl: AbstractDAO<Asset, Long>(), AssetRepository
{
    override fun findAssetById(id: Long): Asset?
    {
        return this.findById(id)
    }
}