package com.unisa.sesalab.ods.repository.tagnfc

import com.unisa.sesalab.ods.model.TagNfc
import com.unisa.sesalab.ods.repository.AbstractDAO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class TagNFCRepositoryImpl(): AbstractDAO<TagNfc, Long>(), TagNFCRepository
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

}
