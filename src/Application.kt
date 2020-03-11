package co2_aggregate_system

import co2_aggregate_system.controller.Co2Concentration
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*

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

        route("co2concentration"){
            val co2 = Co2Concentration()
            get(""){
                call.respond(co2.all())
            }
            get("/now"){
                call.respond(co2.now())
            }
            get("/day"){
                call.respond(co2.day())
            }

            get("/week"){
                call.respond(co2.week())

            }
            get("/month"){
                call.respond(co2.month())

            }

//            get("periodDay/{startDay}/{endDay}"){
//                val startDay: String = call.parameters["startDay"].toString()
//                val endDay: String = call.parameters["endDay"].toString()
//                call.respond(co2.periodDay( startDay,endDay ))
//            }

        }
    }
}

