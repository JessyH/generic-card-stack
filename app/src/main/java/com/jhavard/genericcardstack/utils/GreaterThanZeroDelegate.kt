package com.jhavard.genericcardstack.utils

import java.lang.IllegalStateException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class GreaterThanZeroDelegate<T>(private val defaultValue: T) : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        if (value == null) defaultValue else value!!

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        check(greaterThanZero(value)) {
            "${property.name} must be greater than zero"
        }
        this.value = value
    }

    private fun <T> greaterThanZero(value: T): Boolean =
        when (value) {
            is Int -> value > 0
            is Float -> value > 0.0F
            else -> throw IllegalStateException("Type of value $value not supported")
        }
}