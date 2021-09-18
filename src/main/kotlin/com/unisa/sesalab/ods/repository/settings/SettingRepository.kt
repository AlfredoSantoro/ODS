package com.unisa.sesalab.ods.repository.settings

import com.unisa.sesalab.ods.model.Setting

interface SettingRepository
{
    fun insertSetting(setting: Setting)
    fun updateSetting(setting: Setting)
    fun deleteSettingById(id: Long)
    fun findSettingById(id: Long): Setting?
    fun findAllSettings(): List<Setting>
    fun findByName(name: String): Setting?
}