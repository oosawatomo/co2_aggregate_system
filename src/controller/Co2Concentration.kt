package co2_aggregate_system.controller

import co2_aggregate_system.daos.Co2Records
import co2_aggregate_system.models.Co2Record
import com.google.gson.Gson
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.collections.ArrayList

class Co2Concentration() {

    //現在値取得
    fun now(): String{
        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
        val co2RecordList = ArrayList<Co2Record>()
        val ConversionCo2RecordList = ArrayList<Co2Record>()
        transaction {
            Co2Records
                .selectAll()
                .orderBy(Co2Records.createDatetime to SortOrder.DESC)
                .limit(1)
                .forEach {
                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
        }
        co2RecordList.forEach {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val d:LocalDateTime = LocalDateTime.parse(it.createDatetime, formatter);
            val conversionCo2Record = Co2Record(LocalDateTimeToString(d),it.co2Concentration)
            ConversionCo2RecordList.add(conversionCo2Record)
        }
        return Co2RecordToJson(ConversionCo2RecordList)
    }

    //１日の値取得（５分ピックアップ）
    fun day(): String {
        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
        val co2RecordList = ArrayList<Co2Record>()
        val ConversionCo2RecordList = ArrayList<Co2Record>()
        var ldatetime = LocalDateTime.now().plusDays(-1)
        val eleDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        transaction {
            Co2Records
                //↓日付のwhereが効かない
//                .select({ Co2Records.createDatetime.greaterEq( LocalDateTimeToString(ldatetime) ) })
                .selectAll()
                .orderBy(Co2Records.createDatetime to SortOrder.ASC)
                .forEach {
                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
        }
        co2RecordList.forEach {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val d:LocalDateTime = LocalDateTime.parse(it.createDatetime, formatter);

            if( d > ldatetime ){
                ldatetime = ldatetime.plusMinutes(5)
                val conversionCo2Record = Co2Record(LocalDateTimeToString(ldatetime),it.co2Concentration)
                ConversionCo2RecordList.add(conversionCo2Record)
            }
        }
        if( ldatetime <= eleDatetime ){
            return Co2RecordToJson(ConversionCo2RecordList)
        }

        return Co2RecordToJson(ConversionCo2RecordList)
    }

    fun week(): String{
        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
        val co2RecordList = ArrayList<Co2Record>()
        val ConversionCo2RecordList = ArrayList<Co2Record>()
        var ldatetime = LocalDateTime.now().plusWeeks(-1)
        val eleDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        transaction {
            Co2Records
//                .select({ Co2Records.createDatetime.greaterEq( LocalDateTimeToString(ldatetime) ) })
                .selectAll()
                .orderBy(Co2Records.createDatetime to SortOrder.ASC)
                .forEach {
                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
        }
        co2RecordList.forEach {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val d:LocalDateTime = LocalDateTime.parse(it.createDatetime, formatter);

            if( d > ldatetime ){
                ldatetime = ldatetime.plusMinutes(5)
                val conversionCo2Record = Co2Record(LocalDateTimeToString(ldatetime),it.co2Concentration)
                ConversionCo2RecordList.add(conversionCo2Record)
            }
        }
        if( ldatetime <= eleDatetime ){
            return Co2RecordToJson(ConversionCo2RecordList)
        }

        return Co2RecordToJson(ConversionCo2RecordList)
    }

    fun month(): String{
        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
        val co2RecordList = ArrayList<Co2Record>()
        val ConversionCo2RecordList = ArrayList<Co2Record>()
        var ldatetime = LocalDateTime.now().plusMonths(-1)
        val eleDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        transaction {
            Co2Records
//                .select({ Co2Records.createDatetime.greaterEq( LocalDateTimeToString(ldatetime) ) })
                .selectAll()
                .orderBy(Co2Records.createDatetime to SortOrder.ASC)
                .forEach {
                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
        }
        co2RecordList.forEach {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val d:LocalDateTime = LocalDateTime.parse(it.createDatetime, formatter);

            if( d > ldatetime ){
                ldatetime = ldatetime.plusMinutes(5)
                val conversionCo2Record = Co2Record(LocalDateTimeToString(ldatetime),it.co2Concentration)
                ConversionCo2RecordList.add(conversionCo2Record)
            }
        }
        if( ldatetime <= eleDatetime ){
            return Co2RecordToJson(ConversionCo2RecordList)
        }

        return Co2RecordToJson(ConversionCo2RecordList)

    }

    fun year(): String{
        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
        val co2RecordList = ArrayList<Co2Record>()
        val ConversionCo2RecordList = ArrayList<Co2Record>()
        var ldatetime = LocalDateTime.now().plusYears(-1)
        val eleDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        transaction {
            Co2Records
//                .select({ Co2Records.createDatetime.greaterEq( LocalDateTimeToString(ldatetime) ) })
                .selectAll()
                .orderBy(Co2Records.createDatetime to SortOrder.ASC)
                .forEach {
                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
        }
        co2RecordList.forEach {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val d:LocalDateTime = LocalDateTime.parse(it.createDatetime, formatter);

            if( d > ldatetime ){
                ldatetime = ldatetime.plusMinutes(5)
                val conversionCo2Record = Co2Record(LocalDateTimeToString(ldatetime),it.co2Concentration)
                ConversionCo2RecordList.add(conversionCo2Record)
            }
        }
        if( ldatetime <= eleDatetime ){
            return Co2RecordToJson(ConversionCo2RecordList)
        }

        return Co2RecordToJson(ConversionCo2RecordList)
    }

//    fun periodDay(startDay: String, endDay: String): String{
//        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
//        val co2RecordList = ArrayList<Co2Record>()
//        val datetimePattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
//        val startDayTime = LocalDateTime.parse(startDay + "000000",datetimePattern)
//        val endDayTime = LocalDateTime.parse(endDay + "000000",datetimePattern)
//
//        transaction {
//            Co2Records
//                .select({ Co2Records.createDatetime.between( LocalDateTimeToString(startDayTime), LocalDateTimeToString(endDayTime)  ) })
//                .orderBy(Co2Records.createDatetime to SortOrder.DESC)
//                .forEach {
//                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
//                    co2RecordList.add(co2Record)
//                }
//        }
//
//        return Co2RecordToJson(co2RecordList)
//    }

    fun all(): String{
        Database.connect("jdbc:sqlite:../co2-measure-system/data.sqlite", "org.sqlite.JDBC")
        val co2RecordList = ArrayList<Co2Record>()
        val ConversionCo2RecordList = ArrayList<Co2Record>()
        transaction {
            Co2Records
                .selectAll()
                .orderBy(Co2Records.createDatetime to SortOrder.ASC)
                .forEach {
                    val co2Record = Co2Record(it[Co2Records.createDatetime],it[Co2Records.co2Concentration])
                    co2RecordList.add(co2Record)
                }
        }
        co2RecordList.forEach {
            val formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val d:LocalDateTime = LocalDateTime.parse(it.createDatetime, formatter);
            val conversionCo2Record = Co2Record(LocalDateTimeToString(d),it.co2Concentration)
            ConversionCo2RecordList.add(conversionCo2Record)

        }
        return Co2RecordToJson(ConversionCo2RecordList)
    }

    private fun Co2RecordToJson(co2RecordList: ArrayList<Co2Record>): String{
        val gson = Gson()
        return gson.toJson(co2RecordList) ?: ""
    }

    private fun LocalDateTimeToString(localDateTime: LocalDateTime): String{
        val dateFormat = "yyyy-MM-dd HH:mm:ss"
        return DateTimeFormatter.ofPattern(dateFormat).format(localDateTime) ?: ""
    }
}
