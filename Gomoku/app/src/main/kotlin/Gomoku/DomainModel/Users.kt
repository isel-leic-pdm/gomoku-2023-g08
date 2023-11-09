package Gomoku.DomainModel

import java.net.URL

data class Users(val name: String, val password: String,val token : String? = null, val  url: URL )
data class UsersFake(val name: String, val password: String)
data class GamePlayer(val id: Int, val variantes: variantes, val openingRule: openingrule)