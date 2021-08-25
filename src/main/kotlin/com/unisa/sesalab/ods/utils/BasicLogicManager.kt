package com.unisa.sesalab.ods.utils

import com.unisa.sesalab.ods.repository.BaseCrudRepository

open class BasicLogicManager<T, K>(
        private val repository: BaseCrudRepository<T, K>
)
{
    fun save(entity: T): Long
    {
        return this.repository.save(entity)
    }

    fun findById(entityId: Long): T
    {
        return this.repository.findById(entityId)
    }

    fun delete(entityId: Long)
    {
        this.repository.delete(entityId)
    }

    fun update(entityId: Long, data: K): T
    {
        return this.repository.update(entityId, data)
    }
}