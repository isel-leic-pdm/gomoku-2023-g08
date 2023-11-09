import Gomoku.DomainModel.BOARD_DIM
/*
class Row private constructor(val n: Int) {
    val number = n
    val index = n - 1
    override fun toString() = "Row $n"
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Row) {
            return false
        }
        return this.n == other.n && this.index == other.index
    }
    companion object {
        fun getBoardSize(variante: variantes): Int {
            return when (variante) {
                variantes.OMOK -> 19
                variantes.NORMAL -> 15
            }

            val values = List(BOARD_DIM) { Row(it + 1) }
            operator fun invoke(n: Int) = run {

                println("ROW: $values")
                values[n - 1]
            }
        }
    }
}

fun Int.toRowOrNull(): Row? = if (this in 1 .. BOARD_DIM) Row.values[this - 1] else null
fun Int.toRow(): Row = run{
    require(this in 1 .. BOARD_DIM){throw IllegalArgumentException("Invalid row $this")}
    Row.values[this - 1]
}

 */

import Gomoku.DomainModel.variantes

class Row private constructor(val n: Int) {
    val number = n
    val index = n - 1
    override fun toString() = "Row $n"
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Row) {
            return false
        }
        return this.n == other.n && this.index == other.index
    }

    companion object {
        // Função que retorna o tamanho do tabuleiro com base na variante
        fun getBoardSize(variante: variantes): Int {
            return when (variante) {
                variantes.OMOK -> 19
                variantes.NORMAL -> 15
            }
        }

        val values = List(getBoardSize(variantes.OMOK)) { Row(it + 1) }

        operator fun invoke(n: Int) = run {
            require(n in 1..getBoardSize(variantes.OMOK)) { throw IllegalArgumentException("Invalid row $n") }
            values[n - 1]
        }
    }
}

fun Int.toRowOrNull(): Row? = if (this in 1..Row.getBoardSize(variantes.OMOK)) Row.values[this - 1] else null
fun Int.toRow(): Row = run {
    require(this in 1..Row.getBoardSize(variantes.OMOK)) { throw Exception() }
    Row.values[this - 1]
}








