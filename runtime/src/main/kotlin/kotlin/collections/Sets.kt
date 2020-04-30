/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

@file:OptIn(kotlin.experimental.ExperimentalTypeInference::class)

package kotlin.collections

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// TODO: Add SingletonSet class
/**
 * Returns an immutable set containing only the specified object [element].
 */
public fun <T> setOf(element: T): Set<T> = hashSetOf(element)

/**
 * Builds a new read-only [Set] by populating a [MutableSet] using the given [builderAction]
 * and returning a read-only set with the same elements.
 *
 * The set passed as a receiver to the [builderAction] is valid only inside that function.
 * Using it outside of the function produces an unspecified behavior.
 *
 * Elements of the set are iterated in the order they were added by the [builderAction].
 *
 * @sample samples.collections.Builders.Sets.buildSetSample
 */
@SinceKotlin("1.3")
@ExperimentalStdlibApi
@kotlin.internal.InlineOnly
public actual inline fun <E> buildSet(@BuilderInference builderAction: MutableSet<E>.() -> Unit): Set<E> {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return HashSet<E>().apply(builderAction).build()
}

/**
 * Builds a new read-only [Set] by populating a [MutableSet] using the given [builderAction]
 * and returning a read-only set with the same elements.
 *
 * The set passed as a receiver to the [builderAction] is valid only inside that function.
 * Using it outside of the function produces an unspecified behavior.
 *
 * [capacity] is used to hint the expected number of elements added in the [builderAction].
 *
 * Elements of the set are iterated in the order they were added by the [builderAction].
 *
 * @throws IllegalArgumentException if the given [capacity] is negative.
 *
 * @sample samples.collections.Builders.Sets.buildSetSample
 */
@SinceKotlin("1.3")
@ExperimentalStdlibApi
@kotlin.internal.InlineOnly
public actual inline fun <E> buildSet(capacity: Int, @BuilderInference builderAction: MutableSet<E>.() -> Unit): Set<E> {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return HashSet<E>(capacity).apply(builderAction).build()
}
