package com.unisa.sesalab.ods.model

import com.unisa.sesalab.ods.dto.PeriodicOpeningTimeDTO
import java.time.DayOfWeek
import java.time.OffsetTime
import javax.persistence.*

@Entity(name = "PERIODIC_OPENING_TIMES")
class PeriodicOpeningTime(
        @Column(unique = true)
        val dayOfWeek: DayOfWeek,
        var open: OffsetTime,
        var close: OffsetTime
)
{
    constructor(periodicOpeningTimeDTO: PeriodicOpeningTimeDTO): this(
            periodicOpeningTimeDTO.dayOfWeek,
            periodicOpeningTimeDTO.open,
            periodicOpeningTimeDTO.close
    )

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null
}