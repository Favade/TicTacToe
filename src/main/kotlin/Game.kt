import java.lang.IndexOutOfBoundsException
import java.lang.NumberFormatException

const val EMPTY_PIECE_INDICATOR = ' '
const val FIRST_PLAYER_INDICATOR = 'X'
const val SECOND_PLAYER_INDICATOR = 'O'
fun main() {
    val board = MutableList(3) {MutableList(3) {EMPTY_PIECE_INDICATOR} }
    gameBoard(board)
    game(board)
}

private fun game(board: MutableList<MutableList<Char>>) {
    var turn = 0
    var count = 0
    var gameEnded = false
    var isValid = false
    while (!gameEnded) {
        do {
            try {
                val(x , y) = readln().split(" ").map { it.toInt() - 1 }
                if (board[x][y] == EMPTY_PIECE_INDICATOR && turn % 2 == 0) {
                    board[x][y] = FIRST_PLAYER_INDICATOR
                    gameBoard(board)
                    isValid = true
                } else if (board[x][y] == EMPTY_PIECE_INDICATOR && turn % 2 != 0) {
                    board[x][y] = SECOND_PLAYER_INDICATOR
                    gameBoard(board)
                    isValid = true
                } else if (board[x][y] != EMPTY_PIECE_INDICATOR) {
                    println("This cell is occupied! Choose another one!")
                    isValid = false
                }
            }catch (e: NumberFormatException) {
                println("You should enter numbers!")
            } catch (e: IndexOutOfBoundsException) {
                println("Coordinates should be from 1 to 3!")
            }

        } while (!isValid)

        if (isValid) {
            turn++
            count++
            when {
                winner(board) == FIRST_PLAYER_INDICATOR -> {
                    println("X wins")
                    gameEnded = true
                }
                winner(board) == SECOND_PLAYER_INDICATOR -> {
                    println("O wins")
                    gameEnded = true
                }
                else -> {
                    if (draw(board) == 0) {
                        println("Draw")
                        gameEnded = true
                    }
                }
            }

        }
    }
}

private fun winner(board: MutableList<MutableList<Char>>): Char {
    //diagonal
    if ((board[0][2] != EMPTY_PIECE_INDICATOR) &&
        (board[0][2] == board[1][1]) &&
        (board[0][2] == board[2][0])) {
        return board[0][2]
    }

    if ((board[0][0] != EMPTY_PIECE_INDICATOR) &&
        (board[0][0] == board[1][1]) &&
        (board[0][0] == board[2][2])) {
        return board[0][0]
    }

    // rows
    for (i in board.indices) {
        if ((board[i][0] != EMPTY_PIECE_INDICATOR) &&
            (board[i][0] == board[i][1]) &&
            (board[i][0] == board[i][2])) {
            return board[i][0]
        }
    }

    // columns

    for (j in board.indices) {
        if ((board[0][j] != EMPTY_PIECE_INDICATOR) &&
            (board[0][j] == board[1][j]) &&
            (board[0][j] == board[2][j])) {
            return board[0][j]
        }
    }

    return '_'
}

private fun draw(board: MutableList<MutableList<Char>>) =  board.flatten().count() { it != EMPTY_PIECE_INDICATOR }


private fun gameBoard(board: MutableList<MutableList<Char>>) {
    println("---------")
    for (i in board.indices) {
        print("| ")
        for (j in board[i].indices) {
            print(board[i][j]+ " ")
        }
        print("|")
        println()
    }
    println("---------")
}
