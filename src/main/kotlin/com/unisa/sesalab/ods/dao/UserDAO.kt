package com.unisa.sesalab.ods.dao

import com.unisa.sesalab.ods.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDAO: JpaRepository<User,Long>