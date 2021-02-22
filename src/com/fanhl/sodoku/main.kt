package com.fanhl.sodoku

import com.fanhl.sodoku.algorithm.Sodoku
import com.fanhl.sodoku.util.print

fun main() {
    val board = Sodoku.createBoard()
    val puzzle = Sodoku.createPuzzle(board)
    board.print()
}

