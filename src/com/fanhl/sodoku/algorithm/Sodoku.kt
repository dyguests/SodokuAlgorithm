package com.fanhl.sodoku.algorithm

import com.fanhl.sodoku.util.transpose
import kotlin.math.sqrt

object Sodoku {
    private const val CONST_SIZE = 9
    /**
     * 最大尝试次数
     */
    private const val MAX_TIMES = CONST_SIZE * 500

    fun createBoard(): Array<Array<Int>> {
        val board = Array(9) { Array(9) { 0 } }
        for (i in 0 until CONST_SIZE) {
            for (j in 0 until CONST_SIZE) {
                board[i][j] = ((i * sqrt(CONST_SIZE.toDouble()) + (i / sqrt(CONST_SIZE.toDouble())).toInt() + j) % CONST_SIZE).toInt() + 1
            }
        }
        shuffle(board)
        return board
    }

    private fun shuffle(board: Array<Array<Int>>, times: Int = (8..15).random()) {
        repeat(times) { shuffleOnce(board) }
    }

    private fun shuffleOnce(board: Array<Array<Int>>) {
        // 任选两个不同的数字，交换两种数字的位置
        var chooseNumber = -1
        var replacingNumber = -1
        while (replacingNumber == chooseNumber) {
            chooseNumber = (1..CONST_SIZE).random()
            replacingNumber = (1..CONST_SIZE).random()
        }
        for (i in 0 until CONST_SIZE) {
            for (j in 0 until CONST_SIZE) {
                if (board[i][j] == chooseNumber) {
                    board[i][j] = replacingNumber
                } else if (board[i][j] == replacingNumber) {
                    board[i][j] = chooseNumber
                }
            }
        }

        // 交换任意两行（同宫内）
        // 即1~3(4~6,7~9)行内部交换，但是不会出现第1行与第4行交换
        val sizeOfInnerMatrix = sqrt(CONST_SIZE.toDouble()).toInt()
        var chooseRowIndex = -1
        var replacingRowIndex = -1
        while (chooseRowIndex == replacingRowIndex) {
            chooseRowIndex = (0 until sizeOfInnerMatrix).random()
            replacingRowIndex = (0 until sizeOfInnerMatrix).random()
        }
        val multiplier = (0 until sizeOfInnerMatrix).random()
        chooseRowIndex += (multiplier * sizeOfInnerMatrix)
        replacingRowIndex += (multiplier * sizeOfInnerMatrix)
        board[chooseRowIndex] = board[replacingRowIndex].also {
            board[replacingRowIndex] = board[chooseRowIndex]
        }
        //矩阵转置
        board.transpose()
    }

    fun createPuzzle(board: Array<Array<Int>>): Array<Array<Int>> {
        val puzzle = board.map { it.clone() }.toTypedArray()
        hollowSimple(puzzle)
        hollowAdvanced(puzzle)
        return puzzle
    }

    /**
     * 挖空
     *
     * 保证每个空洞基于当前行、当前行、当前宫只有一个可选值
     */
    private fun hollowSimple(puzzle: Array<Array<Int>>) {
        repeat(MAX_TIMES) {

        }
    }

    /**
     * 挖空
     *
     * 保证整个puzzle只有一个唯一解
     */
    private fun hollowAdvanced(puzzle: Array<Array<Int>>) {

    }
}
