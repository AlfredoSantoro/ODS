package com.unisa.sesalab.ods.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@PropertySource(value =  ["classpath:application.yml"] )
class DB2Config
{
    @Autowired
    protected lateinit var env: Environment

    @Bean("db2DataSource")
    fun db2DataSource(): DataSource
    {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(this.env.getRequiredProperty("datasource.driver-class-name"))
        dataSource.url = this.env.getRequiredProperty("datasource.url")
        dataSource.username = this.env.getRequiredProperty("datasource.username")
        dataSource.password = this.env.getRequiredProperty("datasource.password")
        return dataSource
    }

    @Bean("db2EntityManager")
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean
    {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = this.db2DataSource()
        em.setPackagesToScan("com.unisa.sesalab.ods.model")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }
}