package com.unisa.sesalab.ods

import com.unisa.sesalab.ods.dto.SeatInsertDTO
import com.unisa.sesalab.ods.dto.SettingDTO
import com.unisa.sesalab.ods.dto.TagNfcDTO
import com.unisa.sesalab.ods.model.StudySeat
import com.unisa.sesalab.ods.model.TagNfc
import com.unisa.sesalab.ods.service.seat.SeatService
import com.unisa.sesalab.ods.service.setting.SettingService
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
import java.time.temporal.ChronoUnit

@Component
class DBInitializer(
    private val userService: UserService,
    private val seatsService: SeatService,
    private val tagNFCService: TagNFCService,
    private val settingService: SettingService,
    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private val dbInitType: String
): CommandLineRunner
{
    @Value("\${settings.default-names.reservationHistory}")
    private lateinit var reservationHistorySetting: String

    @Value("\${settings.default-names.reservationDurationHour}")
    private lateinit var reservationDurationHour: String

    @Value("\${settings.default-names.checkInFrequency}")
    private lateinit var checkInFrequency: String

    private val days = 7
    private val reservationDurationInHour = 1
    private val checkInFrequencyToMinutes = 15

    private val logger: Logger = LoggerFactory.getLogger(DBInitializer::class.java)

    private val studySeats = mutableListOf<StudySeat>()

    @Transactional
    override fun run(vararg args: String?)
    {
        if ( this.dbInitType.equals("create", true) )
        {
            this.initUsers()
            this.initSeats()
            this.initSettings()
        }
    }

    private fun initUsers()
    {
        this.logger.info("### Creating users")

        this.userService.signUpUser(CreateAccount("Alfredo", "Santoro",
            "a.santoro73@studenti.unisa.it", "a.santoro73", "lamiapass", AccountType.ADMIN))

        this.userService.signUpUser(CreateAccount("Filomena", "Ferrucci",
            "fferrucci@unisa.it", "fferrucci", "lamiapass", AccountType.ADMIN))

        this.userService.signUpUser(CreateAccount("Fabio", "Palomba",
            "fpalomba@unisa.it", "fpalomba", "lamiapass", AccountType.ADMIN))

        this.userService.signUpUser(CreateAccount("Mario", "Rossi",
            "mariorossi@tesi.it", "mrossi", "lamiapass", AccountType.USER))

        this.logger.info("### Users created")
    }

    private fun initTagNFC(): List<TagNfc>
    {
        this.logger.info("### Creating tag NFC")

        val tagNFC = listOf(this.tagNFCService.createTagNFC(TagNfcDTO("tag1", "0459dc0800ac00")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag2", "0460d409006800")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag3", "0469b40600d200")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag4", "0420e1ab00c000")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag5", "0431c00d000600")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag6", "04a0f482002300")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag7", "0448d802008300")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag8", "0419ed2a00e400")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag9", "04a8e08100f000")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag10", "04b9d58300c700")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag11", "0421858e00b500")),
        this.tagNFCService.createTagNFC(TagNfcDTO("tag12", "0469940c00e500")))

        this.logger.info("### Tag NFC created")

        return tagNFC
    }

    private fun initSeats()
    {
        this.logger.info("### Creating seats")

        this.initTagNFC().forEachIndexed { index, tagNfc ->
            this.studySeats.add(this.seatsService.createSeat(SeatInsertDTO("S$index", true, tagNfc.id!!))!!)
        }

        this.logger.info("### Seats created")
    }

    private fun initSettings()
    {
        this.logger.info("### creating settings")

        // RESERVATION HISTORY
        this.settingService.createSetting(SettingDTO(this.reservationHistorySetting,
            this.days, "reservation history", ChronoUnit.DAYS))

        // RESERVATION DURATION
        this.settingService.createSetting(SettingDTO(this.reservationDurationHour, this.reservationDurationInHour,
        "reservation duration", ChronoUnit.HOURS))

        // CHECK IN FREQUENCY
        this.settingService.createSetting(SettingDTO(this.checkInFrequency, this.checkInFrequencyToMinutes,
            "check-in frequency", ChronoUnit.MINUTES))

        this.logger.info("### Settings created")
    }
}

