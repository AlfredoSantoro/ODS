package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.Seat
import com.unisa.sesalab.ods.model.TagNfc
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.tagnfc.TagNFCService
import com.unisa.sesalab.ods.service.user.UserService
import development.kit.user.AccountType
import development.kit.user.CreateAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class DBInitializer(
    private val userService: UserService,
    private val seatsService: SeatService,
    private val tagNFCService: TagNFCService,
    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private val dbInitType: String
): CommandLineRunner
{
    private val logger: Logger = LoggerFactory.getLogger(DBInitializer::class.java)

    private val seats = mutableListOf<Seat>()

    @Transactional
    override fun run(vararg args: String?)
    {
        if ( this.dbInitType.equals("create", true) )
        {
            this.initUsers()
            this.initSeats()
        }
    }

    private fun initUsers()
    {
        this.logger.info("### Creating users...")

        this.userService.signUpUser(CreateAccount("Alfredo", "Santoro",
            "a.santoro73@studenti.unisa.it", "a.santoro73", "lamiapass", AccountType.ADMIN))

        this.userService.signUpUser(CreateAccount("Filomena", "Ferrucci",
            "fferrucci@unisa.it", "fferrucci", "lamiapass", AccountType.ADMIN))

        this.userService.signUpUser(CreateAccount("Fabio", "Palomba",
            "fpalomba@unisa.it", "fpalomba", "lamiapass", AccountType.ADMIN))

        this.userService.signUpUser(CreateAccount("Mario", "Rossi",
            "mariorossi@tesi.it", "mrossi", "lamiapass", AccountType.USER))

        this.logger.info("### Users created...")
    }

    private fun initTagNFC(): List<TagNfc>
    {
        this.logger.info("### Creating tag NFC...")

        val tagNFC = listOf(this.tagNFCService.createTagNFC(TagNfcDTO("tag1", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag2", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag3", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag4", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag5", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag6", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag7", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag8", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag9", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag10", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag11", "${UUID.randomUUID()}")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag12", "${UUID.randomUUID()}")))

        this.logger.info("### Tag NFC created...")

        return tagNFC
    }

    private fun initSeats()
    {
        this.logger.info("### Creating seats...")

        this.initTagNFC().forEachIndexed { index, tagNfc ->
            this.seats.add(this.seatsService.createSeat(SeatInsertDTO("seat$index", true, tagNfc.id!!))!!)
        }

        this.logger.info("### Seats created...")
    }
}
