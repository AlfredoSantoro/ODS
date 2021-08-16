package com.unisa.sesalab.ods.repository

import org.springframework.transaction.annotation.Transactional

/**
 * A base crud repository
 */
@Transactional
interface BaseRepository<T>
{
    fun save(entity: T): Long
    fun update(entityToUpdate: T): T
    fun findById(entityId: Long): T
    fun delete(entityId: Long)
}