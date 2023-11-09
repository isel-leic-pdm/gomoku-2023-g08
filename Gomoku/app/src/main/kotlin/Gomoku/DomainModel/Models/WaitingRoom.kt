package Gomoku.DomainModel.Models

import Gomoku.DomainModel.openingrule
import Gomoku.DomainModel.variantes

data class WaitingRoom(var player1: Int? = null, var player2: Int? = null, val variante: variantes, val openingRule: openingrule)







