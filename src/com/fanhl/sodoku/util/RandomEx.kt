package com.fanhl.sodoku.util

import java.util.*

fun Int.random() = (0..this).random()
fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start