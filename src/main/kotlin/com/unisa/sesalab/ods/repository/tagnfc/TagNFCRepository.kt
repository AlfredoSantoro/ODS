package com.unisa.sesalab.ods.repository.tagnfc

import com.unisa.sesalab.ods.model.TagNfc

interface TagNFCRepository
{
    fun insertTagNFC(tagNfc: TagNfc): TagNfc
    fun updateTagNFC(tagNfc: TagNfc): TagNfc
    fun deleteTagNFC(id: Long)
    fun findTagNFCById(id: Long): TagNfc?
}