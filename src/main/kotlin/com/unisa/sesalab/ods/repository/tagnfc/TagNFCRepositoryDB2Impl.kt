package com.unisa.sesalab.ods.repository.tagnfc

import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.TagNfc
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class TagNFCRepositoryDB2Impl(
        @Qualifier(value = "db2EntityManager")
        private val em: EntityManager
): TagNFCRepository<TagNfc, TagNfcDTO>
{
    private val logger: Logger = LoggerFactory.getLogger(TagNFCRepositoryDB2Impl::class.java)

    override fun save(entity: TagNfc): Long
    {
        val tagNFCId = this.em.unwrap(Session::class.java).save(entity) as Long
        this.logger.info("new tag-nfc #$tagNFCId successfully saved")
        return tagNFCId
    }

    override fun update(entityId: Long, data: TagNfcDTO): TagNfc
    {
        val session = this.em.unwrap(Session::class.java) as Session
        val databaseSession = session.sessionFactory.openSession()
        this.logger.info("### begin transaction to update TagNfc")
        val tx = databaseSession.beginTransaction()
        val tagNFC = databaseSession.find(TagNfc::class.java, entityId)
        tagNFC.name = data.name
        tagNFC.value = data.value
        databaseSession.flush()
        tx.commit()
        databaseSession.close()
        this.logger.info("### transaction closed to update tagNFC entity")
        this.logger.info("### tagNFC #${tagNFC.id} up to date")
        return tagNFC
    }

    override fun findById(entityId: Long): TagNfc
    {
        return this.em.find(TagNfc::class.java, entityId)
    }

    override fun delete(entityId: Long)
    {
        this.em.remove(this.findById(entityId))
    }

}