package com.fanhl.sodoku

import com.fanhl.sodoku.algorithm.Sodoku
import com.fanhl.sodoku.util.println

fun main() {
    val board = Sodoku.createBoard()
//    val puzzle = Sodoku.createPuzzle(board, 30, 0)
//     val puzzle = Sodoku.createPuzzle(board, 35, 5)
    val puzzle = Sodoku.createPuzzle(board, 45, 15)
    println("board:")
    board.println()
    println("puzzle:")
    puzzle.println()
}

