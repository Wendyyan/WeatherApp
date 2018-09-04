package com.zyf.weatherapp.domain.command

/**
 * Created by zyf on 2018/8/22.
 */
interface Command<T> {

    fun execute(): T

}