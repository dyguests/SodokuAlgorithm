package com.fanhl.sodoku

import com.fanhl.sodoku.algorithm.Sodoku
import com.fanhl.sodoku.util.print

fun main() {
    val board = Sodoku.createBoard()
    val puzzle = Sodoku.createPuzzle(board, 10)
    println("board:")
    board.print()
    println("puzzle:")
    puzzle.print()
}

