package co2_aggregate_system

import co2_aggregate_system.daos.Co2Records
import co2_aggregate_system.models.Co2Record
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)

        }


        get("/json/gson") {
            Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
            val co2RecordList = ArrayList<Co2Record>()

            transaction {
                Co2Records
                    .select({Co2Records.createDatetime.lessEq("2019-11-28 19:20:46") })
                    .orderBy(Co2Records.createDatetime to SortOrder.ASC).forEach {
                    val co2Record: Co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
            }
            val gson = Gson()
            val json = gson.toJson(co2RecordList)
            call.respond(json)
        }
    }
}

