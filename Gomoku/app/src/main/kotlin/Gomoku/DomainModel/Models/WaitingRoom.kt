package Gomoku.DomainModel.Models

import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes

data class WaitingRoom(val id : Int, var playera: Int, var playerb: Int? = null, val variante: variantes, val openingRule: openingrule, var gameID: Int? = null)







