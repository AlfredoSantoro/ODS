package com.unisa.sesalab.ods.repository

import org.hibernate.Session
import java.lang.reflect.ParameterizedType
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root
import javax.transaction.Transactional


@Transactional
abstract class AbstractDAO<E, ID>(
        protected val em: EntityManager
): BaseCrudRepository<E, ID>
{
    private val entityClass: Class<E> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<E>

    protected val session: Session = this.em.unwrap(Session::class.java) as Session
    protected val cb: CriteriaBuilder = this.session.criteriaBuilder
    protected val cq: CriteriaQuery<E> = this.cb.createQuery(this.entityClass)
    protected val root: Root<E> = this.cq.from(this.entityClass)

    override fun delete(entityId: ID)
    {
        this.em.remove(this.findById(entityId))
    }

    override fun save(entity: E): E
    {
        this.em.persist(entity)
        return entity
    }

    override fun update(entity: E): E
    {
        return this.em.merge(entity)
    }

    override fun findById(entityId: ID): E?
    {
        return this.em.find(this.entityClass, entityId)
    }
}