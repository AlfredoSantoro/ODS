package com.unisa.sesalab.ods.service.tagnfc

import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.TagNfc
import com.unisa.sesalab.ods.repository.tagnfc.TagNFCRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TagNFCServiceImpl(
        private val tagNFCRepository: TagNFCRepository
): TagNFCService
{
    private val logger: Logger = LoggerFactory.getLogger(TagNFCServiceImpl::class.java)

    override fun createTagNFC(tagNfcDTO: TagNfcDTO): TagNfc
    {
        return this.tagNFCRepository.insertTagNFC(TagNfc(tagNfcDTO.name, tagNfcDTO.value))
    }

    override fun updateTagNFC(tagNfcDTO: TagNfcDTO): TagNfc?
    {
        return if ( tagNfcDTO.id !== null )
        {
            val tagNFC = this.viewTagNFC(tagNfcDTO.id!!)
            if ( tagNFC !== null )
            {
                tagNFC.name = tagNfcDTO.name
                tagNFC.value = tagNfcDTO.value
                this.tagNFCRepository.updateTagNFC(tagNFC)
            }
            else null
        }
        else null
    }

    override fun deleteTagNFC(id: Long)
    {
       this.tagNFCRepository.deleteTagNFC(id)
    }

    override fun viewTagNFC(id: Long): TagNfc?
    {
        return this.tagNFCRepository.findTagNFCById(id)
    }
}