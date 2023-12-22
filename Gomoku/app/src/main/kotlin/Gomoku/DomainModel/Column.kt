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

        val valuesNormal = List(15) { Column('A' + it) }
        val valuesOmok = List(19) { Column('A' + it) }



    }
    fun toInt(): Int {
        return this.index
    }
    fun toChar(): Char {
        return this.char
    }
}




