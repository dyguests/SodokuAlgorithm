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

    fun createPuzzle(board: Array<Array<Int>>, simpleHole: Int = 30, advancedHole: Int = 0): Array<Array<Int>> {
        val puzzle = board.map { it.clone() }.toTypedArray()
        hollowSimple(puzzle, simpleHole)
        hollowAdvanced(puzzle, advancedHole)
        return puzzle
    }

    /**
     * 挖空
     *
     * 保证每个空洞基于当前行、当前行、当前宫只有一个可选值
     */
    private fun hollowSimple(puzzle: Array<Array<Int>>, cutOff: Int) {
        hollowWithValidator(puzzle, cutOff) { _puzzle, i, j -> hasUniquePossibleNumber(_puzzle, i, j) }
    }

    /**
     * 挖空
     *
     * 保证整个puzzle只有一个唯一解
     */
    private fun hollowAdvanced(puzzle: Array<Array<Int>>, cutOff: Int) {
        hollowWithValidator(puzzle, cutOff) { _puzzle, _, _ -> hasUniqueSolution(_puzzle) }
    }

    /**
     * 挖空
     *
     * 两种挖空的区别仅在于验证方式不一样，这里把验证方法提成参数
     */
    private fun hollowWithValidator(puzzle: Array<Array<Int>>, cutOff: Int, validator: (puzzle: Array<Array<Int>>, i: Int, j: Int) -> Boolean) {
        var removedItems = 0
        repeat(MAX_TIMES) {
            val i = (0 until CONST_SIZE).random()
            val j = (0 until CONST_SIZE).random()
            val temp = puzzle[i][j]
            if (temp == 0) {
                return@repeat
            }
            puzzle[i][j] = 0
            if (validator(puzzle, i, j)) {
                removedItems++
            } else {
                puzzle[i][j] = temp
            }

            if (removedItems == cutOff) {
                return
            }
        }
    }

    private fun hasUniquePossibleNumber(_puzzle: Array<Array<Int>>, i: Int, j: Int) = findPossibleNumbersInPlace(_puzzle, i, j).size == 1

    private fun findPossibleNumbersInPlace(puzzle: Array<Array<Int>>, rowIndex: Int, colIndex: Int): Array<Int> {
        val row = puzzle[rowIndex]
        val col = puzzle.col(colIndex)
        val gong = puzzle.gong(rowIndex, colIndex)
        return ((1..9).toList() - row.asList() - col.asList() - gong.asList()).toTypedArray()
    }

    private fun hasUniqueSolution(puzzle: Array<Array<Int>>): Boolean {
        val numberOfSolutions = findNumberOfSolutions(puzzle)
        // println("numberOfSolutions:$numberOfSolutions")
        return numberOfSolutions == 1
    }

    /**
     * 注意：在整个方法执行过程中，puzzle一直在被改变
     */
    private fun findNumberOfSolutions(puzzle: Array<Array<Int>>): Int {
        val (row, col) = findHole(puzzle) ?: return 1

        var numberOfSolutions = 0

        val possibleNumbers = findPossibleNumbersInPlace(puzzle, row, col)
        for (possibleNumber in possibleNumbers) {
            puzzle[row][col] = possibleNumber
            numberOfSolutions += findNumberOfSolutions(puzzle)
            puzzle[row][col] = 0
        }
        return numberOfSolutions
    }

    private fun findHole(puzzle: Array<Array<Int>>): Pair<Int, Int>? {
        for (i in 0 until CONST_SIZE * CONST_SIZE) {
            val row = i / CONST_SIZE
            val col = i % CONST_SIZE
            if (puzzle[row][col] == 0) {
                return Pair(row, col)
            }
        }
        // println("puzzle solution:")
        // puzzle.println()
        return null
    }

    private fun Array<Array<Int>>.col(colIndex: Int): Array<Int> = map { it[colIndex] }.toTypedArray()

    private fun Array<Array<Int>>.gong(rowIndex: Int, colIndex: Int): Array<Int> {
        val rows = this.copyOfRange(rowIndex / 3 * 3, rowIndex / 3 * 3 + 3)
        val gong = rows.map { it.copyOfRange(colIndex / 3 * 3, colIndex / 3 * 3 + 3) }.toTypedArray()
        return gong.flatten().toTypedArray()
    }
}
