package com.fanhl.sodoku.util

import java.util.*

fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start