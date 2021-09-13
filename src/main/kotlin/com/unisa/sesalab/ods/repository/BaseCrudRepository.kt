package com.unisa.sesalab.ods.repository

/**
 * A Base CRUD Repository
 */
interface BaseCrudRepository<E, ID>
{
    fun saveNewEntity(entity: E): E
    fun update(entity: E): E
    fun findById(entityId: ID): E
    fun delete(entityId: ID)
}