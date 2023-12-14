package Gomoku.DomainModel

import Row

import java.lang.IllegalArgumentException

enum class Direction(val difRow: Int, val difCol: Int) {
    UP(-1,0), DOWN(1,0), LEFT(0,-1),
    RIGHT(0,1), UP_LEFT(-1,-1),
    UP_RIGHT(-1,1), DOWN_LEFT(1,-1), DOWN_RIGHT(1,1)
}

class Cell  constructor(val row: Row, val col: Column) {
    val rowIndex = row.index
    val colIndex = col.index
    override fun toString(): String = "${row.n}${col.char}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Cell
        return row == other.row && col == other.col
    }

    override fun hashCode(): Int {
        return 31 * row.index + col.index
    }
    companion object{
        val valuesNormal = List(15 * 15){ Cell(Row.valuesNormal[it / 15], Column.valuesNormal[it % 15])}
        val valuesOmok = List(19 * 19){ Cell(Row.valuesOmok[it / 19], Column.valuesOmok[it % 19])}
        val INVALID =  Cell(-1, -1)
     }

}
fun Cell(row: Int, col: Int): Cell {
    if (BOARD_DIM == 15) {
        if (row in Row.valuesNormal.indices && col in Column.valuesNormal.indices) {
            return Cell.valuesNormal[row * BOARD_DIM + col]

        } else {
            return Cell.INVALID
        }
    } else {
        if (BOARD_DIM == 19) {
            if (row in Row.valuesOmok.indices && col in Column.valuesOmok.indices) {

                return Cell.valuesOmok[row * BOARD_DIM + col]

            } else {
                return Cell.INVALID
            }
        }
    }
    return Cell.INVALID
}




operator fun Cell.plus(dir: Direction) = Cell(this.rowIndex + dir.difRow, this.colIndex + dir.difCol)



operator fun Cell.minus(other: Cell): Direction = Direction.values().first { it.difRow == other.rowIndex - rowIndex && it.difCol == other.colIndex - colIndex }

operator fun Cell.minus(dir: Direction) = Cell(this.rowIndex - dir.difRow, this.colIndex - dir.difCol)



















