package com.unisa.sesalab.ods.utils

import com.unisa.sesalab.ods.repository.BaseRepository

data class UserLogicManager<T>(
        private val baseRepository: BaseRepository<T>
)
{
    fun saveUser(user: T): Long
    {
        return this.baseRepository.save(user)
    }

    fun findById(userId: Long): T
    {
        return this.baseRepository.findById(userId)
    }

    fun delete(userId: Long)
    {
        this.baseRepository.delete(userId)
    }
}
