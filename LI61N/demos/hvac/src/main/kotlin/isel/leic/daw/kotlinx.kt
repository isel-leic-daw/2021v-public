package isel.leic.daw

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Function that creates a thread-safe wrapper for the given [ReadWriteProperty] instance.
 * Notice that in this context thread-safety refers to:
 * - writes and reads are atomic (i.e. indivisible)
 * - a write is observed by the next read (i.e. an happens-before relation is established between the
 * two operations)
 *
 * If you wish to be able to fully understand the following code, check these out:
 *  - Delegated properties: https://kotlinlang.org/docs/reference/delegated-properties.html
 *  - Object expressions: https://kotlinlang.org/docs/reference/object-declarations.html#object-expressions
 *  - Generics: https://kotlinlang.org/docs/reference/generics.html
 */
fun <T> threadSafe(prop: ReadWriteProperty<Any?, T>): ReadWriteProperty<Any?, T> =
    object: ReadWriteProperty<Any?, T> {
        @kotlin.jvm.Synchronized
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            prop.getValue(thisRef, property)

        @kotlin.jvm.Synchronized
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
            prop.setValue(thisRef, property, value)
    }
