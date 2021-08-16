package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.repository.BaseRepository

data class UserLogic<T, Long>(
        private val baseRepository: BaseRepository<T,Long>
)
{
    fun saveUser(user: T)
    {
        this.baseRepository.save(user)
    }
}
