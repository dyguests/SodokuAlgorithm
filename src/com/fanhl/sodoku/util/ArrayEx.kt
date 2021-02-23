package com.fanhl.sodoku.util

fun Array<Array<Int>>.println() {
    println(joinToString("\n") { it.joinToString(" ") })
}

/**
 * 转置
 *
 * 横竖交换
 *
 * 注：这里默认是正方形矩阵
 */
fun Array<Array<Int>>.transpose() {
    for (i in 0 until size) {
        for (j in i until size) {
            this[i][j] = this[j][i].also {
                this[j][i] = this[i][j]
            }
        }
    }
}
