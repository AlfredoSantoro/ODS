package com.unisa.sesalab.ods.model

import java.time.Duration
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Settings(
        val name: String,
        val duration: Duration
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1
}