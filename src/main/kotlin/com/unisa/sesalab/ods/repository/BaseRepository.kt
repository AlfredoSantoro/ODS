package com.unisa.sesalab.ods.repository

/**
 * A base crud repository
 */
interface BaseRepository<T, Long>
{
    fun save(entity: T)
    fun update(entityToUpdate: T): T
    fun findById(entityId: Long): T
    fun delete(entityId: Long)
}