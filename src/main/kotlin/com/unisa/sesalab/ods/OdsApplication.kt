package com.unisa.sesalab.ods

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OdsApplication
{
    private val logger: Logger = LoggerFactory.getLogger(OdsApplication::class.java)

    init
    {
        sayHello()
    }

    private fun sayHello()
    {
        val javaVersion = System.getProperty("java.version")
        this.logger.info("***********************************************")
        this.logger.info("UNISA SESALAB - Operational Data Store (ODS)")
        this.logger.info("Running on JVM v.$javaVersion")
        this.logger.info("***********************************************")
    }
}

fun main(args: Array<String>)
{
    runApplication<OdsApplication>(*args)
}
