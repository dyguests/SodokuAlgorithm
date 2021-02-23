package com.fanhl.sodoku

fun main() {
    val ints = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val ints1 = ints.copyOfRange(1 / 3 * 3, 1 / 3 * 3 + 3)
    ints1.forEach(::print)
}