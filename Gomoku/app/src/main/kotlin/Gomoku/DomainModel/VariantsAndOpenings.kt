package Gomoku.DomainModel



import Gomoku.DomainModel.Models.GameOutputModel
import kotlin.math.abs

//DONE
fun NormalVariantNormalOpeningRule(game: GameOutputModel, cell: Cell, currentTurn: Int, currentPlayer: Player, userId: Int): GameOutputModel {
    if (game.board.moves[cell] == Player.EMPTY && game.turn == currentTurn) {
            val newBoard: Board = game.board.playNormal(currentPlayer, cell)
            if (newBoard.winner != null) {
                val winner = game.turn
                return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, winner, game.turn)
            } else {
                return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, newBoard.winner, if (userId == game.player1) game.player2 else game.player1)
            }
        } else {
            if (game.winner != 0)
                throw Exception()
            if (game.turn != currentTurn){
                //val responseEntity = ResponseEntity.status(400).body(ErrorResponse("Não é a vez do jogador $currentTurn"))
            throw  Exception()
            }
            else
                throw Exception()
        }
}
//DONE
fun NormalVariantProRule(game: GameOutputModel, cell: Cell, currentTurn: Int, currentPlayer: Player, userId: Int): GameOutputModel {
    val startCell = Cell(BOARD_DIM / 2, BOARD_DIM / 2)
    if (game.board.moves[cell] == Player.EMPTY) {
        if (game.turn == currentTurn) {
            if (game.winner == 0) {
                if (game.board.moves.filter { it.value == Player.PLAYER_X || it.value == Player.PLAYER_O }.size >= 3) {
                    val newBoard: Board = game.board.playNormal(currentPlayer, cell)
                    if (newBoard.winner != null) {
                        val winner = game.turn
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, winner, game.turn)
                    } else {
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, newBoard.winner, if (userId == game.player1) game.player2 else game.player1)
                    }

                } else {
                    if (game.board.moves.filter { it.value == Player.PLAYER_X || it.value == Player.PLAYER_O }
                            .isEmpty()) {//para primeira jogada, colocar peça no centro do board
                        if (cell != startCell) {
                            throw Exception("in pro start cell must be Cell(BOARD_DIM/2, BOARD_DIM/2)")
                        }
                        val newBoard: Board = game.board.playNormal(currentPlayer, cell)
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, game.winner, if (userId == game.player1) game.player2 else game.player1)
                    } else if (game.board.moves[startCell] == currentPlayer
                        && game.board.moves.filter { it.value == currentPlayer }.size == 1
                    ) {

                        if (abs(cell.rowIndex - startCell.rowIndex) >= 3 || abs(cell.colIndex - startCell.colIndex) >= 3) {
                            val newBoard: Board = game.board.playNormal(currentPlayer, cell)
                            return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, game.winner, if (userId == game.player1) game.player2 else game.player1)
                        } else throw Exception("The first player's second stone must be placed at least three intersections away from the first stone")
                    } else {
                        val newBoard: Board = game.board.playNormal(currentPlayer, cell)
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, game.winner, if (userId == game.player1) game.player2 else game.player1)
                    }
                }
            } else throw Exception()

        } else throw Exception("Não é a vez do jogador $currentTurn")
    } else throw Exception()
}


//DONE
fun OmokVariantNormalOpeningRule(game: GameOutputModel, cell: Cell, currentTurn: Int, currentPlayer: Player, userId: Int): GameOutputModel {
    if (game.board.moves[cell] == Player.EMPTY && game.turn == currentTurn) {
        val newBoard: Board = game.board.playOMOK(currentPlayer, cell)
        if (newBoard.winner != null) {
            val winner = game.turn
            return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, winner, game.turn)
        } else {
            return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, newBoard.winner, if (userId == game.player1) game.player2 else game.player1)
        }
    } else {
        if (game.winner != 0)
            throw Exception()
        if (game.turn != currentTurn)
            throw Exception("Não é a vez do jogador $currentTurn")
        else
            throw Exception()

    }
}


fun OmokVariantProRule(game: GameOutputModel, cell: Cell, currentTurn: Int, currentPlayer: Player, userId: Int): GameOutputModel {
    val startCell = Cell(BOARD_DIM / 2, BOARD_DIM / 2) // 10J
    if (game.board.moves[cell] == Player.EMPTY) {
        if (game.turn == currentTurn) {
            if (game.winner == 0) {
                if (game.board.moves.filter { it.value == Player.PLAYER_X || it.value == Player.PLAYER_O }.size >= 3) {
                    val newBoard: Board = game.board.playOMOK(currentPlayer, cell)
                    if (newBoard.winner != null) {
                        val winner = game.turn
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, winner, game.turn)
                    } else {
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, newBoard.winner, if (userId == game.player1) game.player2 else game.player1)
                    }

                } else {
                    if (game.board.moves.filter { it.value == Player.PLAYER_X || it.value == Player.PLAYER_O }
                            .isEmpty()) {//para primeira jogada, colocar peça no centro do board
                        if (cell != startCell) {
                            throw Exception("in pro start cell must be Cell(BOARD_DIM/2, BOARD_DIM/2)")
                        }
                        val newBoard: Board = game.board.playNormal(currentPlayer, cell)
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, game.winner, if (userId == game.player1) game.player2 else game.player1)
                    } else if (game.board.moves[startCell] == currentPlayer
                        && game.board.moves.filter { it.value == currentPlayer }.size == 1
                    ) {

                        if (abs(cell.rowIndex - startCell.rowIndex) >= 3 || abs(cell.colIndex - startCell.colIndex) >= 3) {
                            val newBoard: Board = game.board.playOMOK(currentPlayer, cell)
                            return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, game.winner, if (userId == game.player1) game.player2 else game.player1
                            )
                        } else throw Exception("The first player's second stone must be placed at least three intersections away from the first stone")
                    } else {
                        val newBoard: Board = game.board.playNormal(currentPlayer, cell)
                        return GameOutputModel(game.id, newBoard, game.player1, game.player2, game.variante, game.openingRule, game.winner, if (userId == game.player1) game.player2 else game.player1)
                    }
                }
            } else throw Exception()

        } else throw Exception("Não é a vez do jogador $currentTurn")
    } else throw Exception()

}





