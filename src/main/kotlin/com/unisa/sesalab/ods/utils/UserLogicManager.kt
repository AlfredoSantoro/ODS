package com.unisa.sesalab.ods.utils

import com.unisa.sesalab.ods.repository.BaseCrudRepository

data class UserLogicManager<T, K>(
        private val repo: BaseCrudRepository<T, K>
)
{
    fun saveUser(entity: T): Long
    {
        return this.repo.save(entity)
    }

    fun findById(entityId: Long): T
    {
        return this.repo.findById(entityId)
    }

    fun delete(entityId: Long)
    {
        this.repo.delete(entityId)
    }

    fun update(entityId: Long, data: K): T
    {
        return this.repo.update(entityId, data)
    }
}
