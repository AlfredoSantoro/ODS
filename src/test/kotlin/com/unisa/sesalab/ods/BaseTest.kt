package com.unisa.sesalab.ods

//import com.unisa.sesalab.ods.repository.seats.SeatRepositoryDB2Impl
//import com.unisa.sesalab.ods.repository.tagnfc.TagNFCRepositoryDB2Impl
import com.unisa.sesalab.ods.repository.reservations.ReservationRepositoryDB2Impl
import com.unisa.sesalab.ods.repository.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [OdsApplication::class])
abstract class BaseTest
{

    @Autowired
    protected lateinit var reservationRepositoryDB2Impl: ReservationRepositoryDB2Impl

    @Autowired
    protected lateinit var userRepositoryImpl: UserRepository

   // @Autowired
    //protected lateinit var seatRepositoryDB2Impl: SeatRepositoryDB2Impl

    // @Autowired
    // protected lateinit var tagNFCRepositoryDB2Impl: TagNFCRepositoryDB2Impl
}