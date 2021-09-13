package com.unisa.sesalab.ods.repository

import java.lang.reflect.ParameterizedType
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Transactional
abstract class AbstractDAO<E, ID>: BaseCrudRepository<E, ID>
{
    @PersistenceContext
    protected lateinit var em: EntityManager

    private val entityClass: Class<E> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<E>

    override fun delete(entityId: ID)
    {
        this.em.remove(this.findById(entityId))
    }

    override fun saveNewEntity(entity: E): E
    {
        this.em.persist(entity)
        return entity
    }

    override fun update(entity: E): E
    {
        return this.em.merge(entity)
    }

    override fun findById(entityId: ID): E
    {
        return this.em.find(this.entityClass, entityId)
    }
}