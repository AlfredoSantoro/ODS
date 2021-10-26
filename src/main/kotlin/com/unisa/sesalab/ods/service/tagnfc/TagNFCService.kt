package com.unisa.sesalab.ods.service.tagnfc

import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.TagNfc

interface TagNFCService
{
    fun createTagNFC(tagNfcDTO: TagNfcDTO): TagNfc
    fun updateTagNFC(tagNfcDTO: TagNfcDTO): TagNfc?
    fun deleteTagNFC(id: Long)
    fun findTagNFCById(id: Long): TagNfc?
    fun findTagNFCByValue(uid: String): TagNfc?
}