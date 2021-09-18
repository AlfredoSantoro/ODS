package com.unisa.sesalab.ods.repository.asset

import com.unisa.sesalab.ods.model.Asset
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class AssetRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<Asset, Long>(entityManager), AssetRepository
{
    override fun findAssetById(id: Long): Asset?
    {
        return this.findById(id)
    }
}