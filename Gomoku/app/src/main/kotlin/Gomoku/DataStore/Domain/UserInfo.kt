package Gomoku.DataStore.Domain

/**
 * Represents the user information, to be used while he is in the lobby waiting for
 * an opponent and during the game. Initialization parameters must be valid as specified by the
 * [validateUserInfoParts] function.
 * @property [id] the user's nick name.
 * @property [password] the user's moto, if he has one.
 */
data class UserInfo(val id: Int, val password: String? = null, val token : String? = null) {
    init {
        require(validateUserInfoParts(id, password))
    }
}

/**
 * Checks whether the received values are acceptable as [UserInfo] instance fields.
 * @param [id] the user's nick name. It cannot be blank.
 * @param [pass] the user's moto, if he has one. If present, it cannot be blank.
 * @return true if the values are acceptable, false otherwise.
 */
fun validateUserInfoParts(id: Int, pass: String?): Boolean {
    if (id == 0) return false
    if (pass != null && pass.isBlank()) return false
    return true
}

/**
 * Creates a [UserInfo] instance if the received values are acceptable as [UserInfo] instance fields.
 * Otherwise, returns null.
 */
fun toUserInfoOrNull(id: Int, pass: String?): UserInfo? =
    if (validateUserInfoParts(id, pass))
        UserInfo(id, pass)
    else
        null