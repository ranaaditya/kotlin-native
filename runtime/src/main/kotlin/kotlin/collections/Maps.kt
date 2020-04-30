/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

@file:OptIn(kotlin.experimental.ExperimentalTypeInference::class)

package kotlin.collections

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Builds a new read-only [Map] by populating a [MutableMap] using the given [builderAction]
 * and returning a read-only map with the same key-value pairs.
 *
 * The map passed as a receiver to the [builderAction] is valid only inside that function.
 * Using it outside of the function produces an unspecified behavior.
 *
 * Entries of the map are iterated in the order they were added by the [builderAction].
 *
 * @sample samples.collections.Builders.Maps.buildMapSample
 */
@SinceKotlin("1.3")
@ExperimentalStdlibApi
@kotlin.internal.InlineOnly
public actual inline fun <K, V> buildMap(@BuilderInference builderAction: MutableMap<K, V>.() -> Unit): Map<K, V> {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return HashMap<K, V>().apply(builderAction).build()
}

/**
 * Builds a new read-only [Map] by populating a [MutableMap] using the given [builderAction]
 * and returning a read-only map with the same key-value pairs.
 *
 * The map passed as a receiver to the [builderAction] is valid only inside that function.
 * Using it outside of the function produces an unspecified behavior.
 *
 * [capacity] is used to hint the expected number of pairs added in the [builderAction].
 *
 * Entries of the map are iterated in the order they were added by the [builderAction].
 *
 * @throws IllegalArgumentException if the given [capacity] is negative.
 *
 * @sample samples.collections.Builders.Maps.buildMapSample
 */
@SinceKotlin("1.3")
@ExperimentalStdlibApi
@kotlin.internal.InlineOnly
public actual inline fun <K, V> buildMap(capacity: Int, @BuilderInference builderAction: MutableMap<K, V>.() -> Unit): Map<K, V> {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return HashMap<K, V>(capacity).apply(builderAction).build()
}


// creates a singleton copy of map, if there is specialization available in target platform, otherwise returns itself
@Suppress("NOTHING_TO_INLINE")
internal inline actual fun <K, V> Map<K, V>.toSingletonMapOrSelf(): Map<K, V> = toSingletonMap()

// creates a singleton copy of map
internal actual fun <K, V> Map<out K, V>.toSingletonMap(): Map<K, V>
        = with(entries.iterator().next()) { mutableMapOf(key to value) }


/**
 * Native map and set implementations do not make use of capacities or load factors.
 */
@PublishedApi
internal actual fun mapCapacity(expectedSize: Int) = expectedSize
