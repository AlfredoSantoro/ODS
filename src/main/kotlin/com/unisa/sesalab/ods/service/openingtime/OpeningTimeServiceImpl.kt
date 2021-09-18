package com.unisa.sesalab.ods.service.openingtime

import com.unisa.sesalab.ods.dto.PeriodicOpeningTimeDTO
import com.unisa.sesalab.ods.dto.PeriodicOpeningTimeUpdateDTO
import com.unisa.sesalab.ods.exception.PeriodicOpeningTimeConstraintsException
import com.unisa.sesalab.ods.model.PeriodicOpeningTime
import com.unisa.sesalab.ods.repository.openingtime.OpeningTimeRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OpeningTimeServiceImpl(
        private val openingTimeRepository: OpeningTimeRepository
): OpeningTimeService
{
    private val logger: Logger = LoggerFactory.getLogger(OpeningTimeServiceImpl::class.java)

    override fun insertPeriodicOpeningTime(periodicOpeningTimeDTO: PeriodicOpeningTimeDTO)
    {
        if ( this.openingTimeRepository.findByDayOfWeek(periodicOpeningTimeDTO.dayOfWeek) !== null )
        {
            this.logger.error("### cannot create a periodic opening time with a dayOfWeek already existing in the database")
            throw PeriodicOpeningTimeConstraintsException("cannot create a periodic opening time with a dayOfWeek already existing in the database")
        }
        else
        {
            this.openingTimeRepository.insertOpeningTime(PeriodicOpeningTime(periodicOpeningTimeDTO))
        }
    }

    override fun updateOpeningTime(openingTimeUpdateDTO: PeriodicOpeningTimeUpdateDTO)
    {
        val openingTime = this.openingTimeRepository.findOpeningTimeById(openingTimeUpdateDTO.id)
        if ( openingTime !== null )
        {
            openingTime.open = openingTimeUpdateDTO.open
            openingTime.close = openingTimeUpdateDTO.close
            this.openingTimeRepository.updateOpeningTime(openingTime)
        }
        else
        {
            throw PeriodicOpeningTimeConstraintsException("opening time #${openingTimeUpdateDTO.id} not found")
        }
    }

    override fun deleteOpeningTimeById(id: Long)
    {
        this.openingTimeRepository.deleteOpeningTimeById(id)
    }

    override fun findAll(): List<PeriodicOpeningTime>
    {
        return this.openingTimeRepository.findAll()
    }
}