package com.unisa.sesalab.ods.service.setting

import com.unisa.sesalab.ods.dto.SettingDTO
import com.unisa.sesalab.ods.dto.SettingUpdateDTO
import com.unisa.sesalab.ods.model.Setting

interface SettingService
{
    fun createSetting(settingDTO: SettingDTO)
    fun updateSetting(settingUpdateDTO: SettingUpdateDTO)
    fun findSettingById(id: Long): Setting?
    fun deleteSetting(id: Long)
    fun finAllSettings(): List<Setting>
    fun findByName(name: String): Setting?
}