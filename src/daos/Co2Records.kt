package co2_aggregate_system.daos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Date
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object Co2Records : Table("co2_records") {
    val createDatetime = varchar("create_datetime",255).primaryKey()
    val co2Concentration: Column<Int> = integer("co2_concentration")
}