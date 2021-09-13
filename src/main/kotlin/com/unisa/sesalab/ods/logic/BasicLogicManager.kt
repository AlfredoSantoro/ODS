/*
package com.unisa.sesalab.ods.logic

import com.unisa.sesalab.ods.repository.BaseCrudRepository

abstract class BasicLogicManager<T, K>(
        private val repository: BaseCrudRepository<T, K>
)
{
    protected fun save(entity: T): Long
    {
        return this.repository.saveNewEntity(entity)
    }

    protected fun findById(entityId: Long): T
    {
        return this.repository.findById(entityId)
    }

    protected fun delete(entityId: Long)
    {
        this.repository.delete(entityId)
    }

    protected fun update(entityId: Long, data: K): T
    {
        return this.repository.update(entityId, data)
    }
}*/
