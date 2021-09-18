package com.unisa.sesalab.ods.repository.settings

import com.unisa.sesalab.ods.model.Setting
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class SettingRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<Setting, Long>(entityManager), SettingRepository
{
    override fun insertSetting(setting: Setting) { this.save(setting) }

    override fun updateSetting(setting: Setting) { this.update(setting) }

    override fun deleteSettingById(id: Long) { this.delete(id) }

    override fun findSettingById(id: Long): Setting? { return this.findById(id) }

    override fun findAllSettings(): List<Setting>
    {
        val query = this.em.createQuery("select setting from Setting as setting", Setting::class.java)
        return query.resultList
    }

    override fun findByName(name: String): Setting?
    {
        val query = this.em.createQuery("select setting from Setting as setting where lower(setting.name) = :name", Setting::class.java)
        query.setParameter("name", name.lowercase())
        return try {
            query.singleResult
        }
        catch (error: NoResultException)
        {
            null
        }
    }
}