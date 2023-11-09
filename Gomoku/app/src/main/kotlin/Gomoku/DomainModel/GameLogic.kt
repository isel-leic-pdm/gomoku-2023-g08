package Gomoku.DomainModel


import Gomoku.DomainModel.Models.GameOutputModel
import toRow

typealias Moves = Map<Cell, Player>

fun gameplay(game: GameOutputModel, userId: Int, l: Int, c: Char): GameOutputModel {
    val variante = game.variante
    val openingRule = game.openingRule
    val currentTurn = userId
    val currentPlayer = if (userId == game.player1) Player.PLAYER_X else Player.PLAYER_O
    val row = l.toRow()
    val col = c.toColumn()
    val cell = Cell(row, col)
    if(variante == variantes.NORMAL){
        if(openingRule == openingrule.NORMAL){ // normal, normal, joga se fressytle
            return NormalVariantNormalOpeningRule(game, cell, currentTurn, currentPlayer, userId)
        }
        else {
            if(openingRule== openingrule.PRO){
                return NormalVariantProRule(game, cell, currentTurn, currentPlayer, userId)
            }
        }
    } else {
        if(variante == variantes.OMOK){
            if(openingRule == openingrule.NORMAL){
                return OmokVariantNormalOpeningRule(game, cell, currentTurn, currentPlayer, userId)
            }
            else {
                if(openingRule== openingrule.PRO){
                    return OmokVariantProRule(game, cell, currentTurn, currentPlayer, userId)
                }
            }

        }

    }
    return game
}


fun Board.playOMOK(currPlayer: Player, cell: Cell): Board {
    val board  = Board(this.moves, currPlayer)
    val allcells = board.moves.toMutableMap()
    var celltoplay =  allcells.filterKeys { it.col == cell.col && it.row == cell.row }.values.firstOrNull()
    if (board.turn == currPlayer) {
        if (celltoplay == Player.EMPTY || celltoplay == Player.SPACING  || celltoplay == null) {
            celltoplay = currPlayer
            allcells[cell] = celltoplay
            this.lastMove = Pair(cell, currPlayer)

            val newMoves =  moves + Pair(cell, currPlayer)
            when {
                isWinOMOK(newMoves) -> {
                    println("O jogo acabou. O jogador $currPlayer venceu!")
                    return Board(allcells, currPlayer, winner = currPlayer)
                }
                moves.size == GAME_SIZE -> {
                    println("O jogo acabou, Deu empate!")
                    return Board(allcells, currPlayer)
                }
                else -> {
                    this.turn = currPlayer.other()
                    return Board(allcells, this.turn)
                }
            }


        }
    }
    else
    {
        throw Exception("Não é a vez do jogador $currPlayer")
    }

    return Board(allcells,this.turn)
}

fun Board.playNormal(currPlayer: Player, cell: Cell): Board {
    val board  = Board(this.moves, currPlayer)
    val allcells = board.moves.toMutableMap()
    var celltoplay =  allcells.filterKeys { it.col == cell.col && it.row == cell.row }.values.firstOrNull()
    if (board.turn == currPlayer) {
        if (celltoplay == Player.EMPTY || celltoplay == Player.SPACING  || celltoplay == null) {
            celltoplay = currPlayer
            allcells[cell] = celltoplay
            this.lastMove = Pair(cell, currPlayer)

            val newMoves =  moves + Pair(cell, currPlayer)
            when {
                isWin(newMoves) -> {
                    println("O jogo acabou. O jogador $currPlayer venceu!")
                    return Board(allcells, currPlayer, winner = currPlayer)
                }
                moves.size == GAME_SIZE -> {
                    println("O jogo acabou, Deu empate!")
                    return Board(allcells, currPlayer)
                }
                else -> {
                    this.turn = currPlayer.other()
                    return Board(allcells, this.turn)
                }
            }


        }
    }
    else
    {
        throw Exception("Não é a vez do jogador $currPlayer")
    }

    return Board(allcells,this.turn)
}




