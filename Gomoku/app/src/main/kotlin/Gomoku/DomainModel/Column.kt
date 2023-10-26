package Gomoku.DomainModel


import java.lang.IllegalArgumentException


class Column private constructor(val char: Char) {
    val symbol = char
    val index = char - 'A'
    override fun toString() = "Column $char"
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Column) {
            return false
        }
        return this.char == other.char && this.index == other.index
    }

    companion object {
        // Função que retorna o tamanho da coluna com base na variante
        fun getColumnSize(variante: variantes): Int {
            return when (variante) {
                variantes.OMOK -> 19
                variantes.NORMAL -> 15
            }
        }

        val values = List(getColumnSize(variantes.OMOK)) { Column('A' + it) }

        operator fun invoke(char: Char) = run {
            require(char in 'A'..'A' + getColumnSize(variantes.OMOK)) { throw IllegalArgumentException("Invalid column $char") }
            values[(char - 'A')]
        }
    }
}

fun Char.toColumnOrNull(): Column? = if (this in 'A'..'H') Column.values[this - 'A'] else null
fun Char.toColumn(): Column = run {
   require(this in 'A'..'T') { throw Exception() }
    Column.values[this - 'A']
}


