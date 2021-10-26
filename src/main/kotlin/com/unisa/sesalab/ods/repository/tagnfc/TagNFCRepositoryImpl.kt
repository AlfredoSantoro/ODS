package com.unisa.sesalab.ods.repository.tagnfc

import com.unisa.sesalab.ods.model.TagNfc
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class TagNFCRepositoryImpl(
        entityManager: EntityManager
): AbstractDAO<TagNfc, Long>(entityManager), TagNFCRepository
{
    private val logger: Logger = LoggerFactory.getLogger(TagNFCRepositoryImpl::class.java)

    override fun insertTagNFC(tagNfc: TagNfc): TagNfc
    {
        return this.save(tagNfc)
    }

    override fun updateTagNFC(tagNfc: TagNfc): TagNfc
    {
        return this.update(tagNfc)
    }

    override fun deleteTagNFC(id: Long)
    {
        this.delete(id)
    }

    override fun findTagNFCById(id: Long): TagNfc?
    {
        return this.findById(id)
    }

    override fun findTagNFCByValue(uid: String): TagNfc?
    {
        val q = this.em.createQuery("select tag from TagNfc as tag where tag.value = :uid", TagNfc::class.java)
        q.setParameter("uid", uid)
        return try
        {
            q.singleResult
        }
        catch (error: NoResultException)
        {
            this.logger.info("### tag nfc value -> $uid not found")
            null
        }
    }

}
