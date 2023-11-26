package Gomoku.DomainModel

import java.net.URL

data class Users(val id : Int?= null,val name: String?, val password: String?,val token : String? = null, val  url: URL?=null )
data class UserLog(val id:Int, val token: String)
data class UsersFake(val name: String, val password: String)
data class GamePlayer(val id: Int, val variantes: variantes, val openingRule: openingrule)