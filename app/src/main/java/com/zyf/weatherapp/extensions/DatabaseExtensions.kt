package com.zyf.weatherapp.extensions

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

/**
 * Created by zyf on 2018/8/23.
 */
fun <T : Any> SelectQueryBuilder.parseList(parse: (Map<String, Any?>) -> T): List<T> =
        parseList(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parse(columns)
        })

fun <T : Any> SelectQueryBuilder.parseOpt(parse: (Map<String, Any?>) -> T): T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parse(columns)
        })

fun SQLiteDatabase.clear(tableName: String){
    execSQL("delete from $tableName")
}

fun SelectQueryBuilder.byId(id: Long): SelectQueryBuilder =
        whereSimple("_id = ?", id.toString())

fun <K, V : Any> MutableMap<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map {Pair(it.key, it.value!!)}.toTypedArray()

inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R{
    for (element in this){
        val result = predicate(element)
        if (result != null)
            return result
    }
    throw NoSuchElementException("No element matching predicate was found")
}