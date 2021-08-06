import edu.princeton.cs.algs4.StdRandom
import edu.princeton.cs.algs4.StdStats
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertTrue

class PercolationTest {

    val percolation = Percolation(20)


    @Test
    fun testThisShit() {

        var percolates = false
        var loops = 0
        while (percolates.not()) {
            val row = Random.nextInt(0 until 20)
            val column = Random.nextInt(0 until 20)

            if (percolation.isFull(row, column)) {
                percolation.open(row, column)
                //percolation.printGrid()
                //println("NEXT ITER")
                //println("__________")
                //percolation.printGridIds()
                percolates = percolation.percolates()
                if (percolates) {
                    percolation.printGrid()
                }
                loops++
            }
        }
        println("Loops $loops")
        //StdStats.plotPoints()
    }
}