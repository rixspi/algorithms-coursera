import edu.princeton.cs.algs4.StdDraw
import edu.princeton.cs.algs4.WeightedQuickUnionUF
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import javax.swing.table.TableColumn


//https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php

class Percolation(private val n: Int) {

    val grid: Array<Array<Site>>
    private val quickUnionUF: WeightedQuickUnionUF

    class Site(val id: Int, var open: Boolean)

    private val topSite = Site(n * n, false)
    private val bottomSite = Site(n * n + 1, false)

    init {
        if (n <= 0) {
            throw IllegalArgumentException("n: $n has to be bigger than 1")
        }
        var index = 0
        grid = Array(n) {
            Array(n) {
                Site(index, false).also {
                    index++
                }
            }
        }
        quickUnionUF = WeightedQuickUnionUF(n * n + 2)

        grid[0].forEach {
            quickUnionUF.union(topSite.id, it.id)
        }

        grid[n - 1].forEach {
            quickUnionUF.union(bottomSite.id, it.id)
        }
    }

    fun open(row: Int, column: Int) {
        if (!isOpen(row, column)) {
            val site = grid[row][column]
            site.open = true
            checkAdjacentOpenSites(row, column)
        }
    }

    private fun union(row: Int, column: Int, nRow: Int, nColumn: Int) {
        if (checkRowAndColumnInRange(row, column).not()) {
            return
        }
        if (checkRowAndColumnInRange(nRow, nColumn).not()) {
            return
        }
        val thisFellow = grid[row][column]
        val neighbour = grid[nRow][nColumn]

        neighbour.run {
            if (open) {
                quickUnionUF.union(thisFellow.id, id)
            }
        }
    }

    private fun checkAdjacentOpenSites(row: Int, column: Int) {
        union(row, column, row - 1, column)
        union(row, column, row + 1, column)
        union(row, column, row, column - 1)
        union(row, column, row, column + 1)
    }

    fun isOpen(row: Int, column: Int): Boolean {
        return grid[row][column].open
    }

    fun isFull(row: Int, column: Int): Boolean {
        validateRowAndColumn(row, column)
        return grid[row][column].open.not()
    }

    fun numberOfOpenSites(): Int {
        return grid.sumOf { innerArr -> innerArr.count { it.open } }
    }

    fun percolates(): Boolean = quickUnionUF.connected(topSite.id, bottomSite.id)

    fun percolatesCheckOneBeOne(): Boolean {
        val top = grid[0]
        val bottom = grid[n - 1]

        top.forEach { topSites ->
            bottom.forEach { bottomSites ->
                val percolates = quickUnionUF.connected(topSites.id, bottomSites.id)
                if (percolates) {
                    return true
                }
            }
        }
        return false
    }

    private fun validateRowAndColumn(row: Int, column: Int) {
        if ((row in 0 until n).not() || (column in 0 until n).not()) {
            throw IllegalArgumentException("Row $row and column $column has to at least 1 and no bigger than $n")
        }
    }

    private fun checkRowAndColumnInRange(row: Int, column: Int): Boolean {
        if ((row in 0 until n).not() || (column in 0 until n).not()) {
            return false
        } else {
            return true
        }
    }
}
