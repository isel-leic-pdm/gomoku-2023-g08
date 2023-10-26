package Gomoku.DomainModel.Models


import Gomoku.DomainModel.Board
import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes
import java.util.*


data class GamePlayInputModel(val userId : Int, val l: Int, val c: Char)
data class GameOutputModel(val id: Int, val board: Board, val player1: Int, val player2: Int, val variante: variantes,val openingRule : openingrule,  val winner: Int? = null, val turn: Int){
}
data class UserOutputModel( val username : String, val password: String, var token : String?= null)
data class RankingOutputModel(val username : String, val vitorias : Int, val ranking: Int, val jogos: Int)
