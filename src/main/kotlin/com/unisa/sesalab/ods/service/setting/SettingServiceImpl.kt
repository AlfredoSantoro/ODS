package com.unisa.sesalab.ods.service.setting

import com.unisa.sesalab.ods.dto.SettingDTO
import com.unisa.sesalab.ods.dto.SettingUpdateDTO
import com.unisa.sesalab.ods.exception.SettingException
import com.unisa.sesalab.ods.model.Setting
import com.unisa.sesalab.ods.repository.settings.SettingRepository
import org.springframework.stereotype.Service

@Service
class SettingServiceImpl(
        private val settingRepository: SettingRepository
): SettingService
{
    override fun createSetting(settingDTO: SettingDTO)
    {
        if ( this.settingRepository.findByName(settingDTO.name) == null )
        {
            this.settingRepository.insertSetting(Setting(settingDTO.name, settingDTO.value, settingDTO.description, settingDTO.representationUnit))
        }
        else
        {
            throw SettingException("### cannot create a new setting with a name already exist")
        }
    }

    override fun updateSetting(settingUpdateDTO: SettingUpdateDTO)
    {
        val setting = this.settingRepository.findSettingById(settingUpdateDTO.id)
        if ( setting == null ) throw SettingException("### setting #${settingUpdateDTO.id} not found")
        else
        {
            setting.value = settingUpdateDTO.value
            setting.description = settingUpdateDTO.description
            setting.representationUnit = settingUpdateDTO.representationUnit
            this.settingRepository.updateSetting(setting)
        }
    }

    override fun findSettingById(id: Long): Setting? { return this.settingRepository.findSettingById(id) }

    override fun deleteSetting(id: Long) { this.settingRepository.deleteSettingById(id) }

    override fun finAllSettings(): List<Setting> { return this.settingRepository.findAllSettings() }

    override fun findByName(name: String): Setting? { return this.settingRepository.findByName(name) }
}