package com.unisa.sesalab.ods.repository

/**
 * A base crud repository
 */
interface BaseCrudRepository<T, K>
{
    fun save(entity: T): Long
    fun update(entityId: Long, data: K): T
    fun findById(entityId: Long): T
    fun delete(entityId: Long)
}