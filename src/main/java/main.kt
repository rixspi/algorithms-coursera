import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import edu.princeton.cs.algs4.StdDraw
import kotlin.random.Random
import kotlin.random.nextInt
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

class PercolationState(private val size: Int) {
    private val percolation by mutableStateOf(Percolation(size))
    var grid by mutableStateOf(percolation.grid)

    fun start() {

    }

    fun percolates() = percolation.percolates()

    fun update() {
        val row = Random.nextInt(0 until size)
        val column = Random.nextInt(0 until size)

        if (percolation.isFull(row, column)) {
            percolation.open(row, column)
        }
        grid = percolation.grid.copyOf()
    }
}

fun main() = application {
    //percolation()

    val windowSize = 600.dp
    val gridSize = 1000
    val boxSize = (windowSize-40.dp).div(gridSize.toDouble().dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = windowSize, height = windowSize)
    ) {
        val percolation = remember { PercolationState(gridSize) }

        Canvas(modifier = Modifier.fillMaxSize()) {
            percolation.grid.forEachIndexed { row, it ->
                    it.forEachIndexed { column, site ->
                        if (site.open) {
                            drawRect(Color.White, topLeft = Offset(column*boxSize, row*boxSize), size = Size(boxSize, boxSize))
                        }else{
                            drawRect(Color.Black, topLeft = Offset(column*boxSize, row*boxSize), size = Size(boxSize, boxSize))
                        }
                    }
            }
        }

//        Column(Modifier.fillMaxSize()) {
//            percolation.grid.forEach {
//                Row {
//                    it.forEach {
//                        if (it.open) {
//                            Box(
//                                modifier = Modifier.size(boxSize.dp).clip(RectangleShape).background(Color.White)
//                            )
//                        }else{
//                            Box(
//                                modifier = Modifier.size(boxSize.dp).clip(RectangleShape).background(Color.Black)
//                            )
//                        }
//                    }
//                }
//            }
//
//        }
        LaunchedEffect(Unit) {

            while (true) {
                //delay(500)
                withFrameMillis {
                    if (percolation.percolates().not()) {
                        percolation.update()
                    }
                }
            }
        }
    }
}

fun percolation() {
    println("Hello World!")

    val size = 50
    val tests = 1
    val results = IntArray(tests)

    for (i in 0 until tests) {
        val percolation = Percolation(size)
        var percolates = false
        var loops = 0
        while (percolates.not()) {
            val row = Random.nextInt(0 until size)
            val column = Random.nextInt(0 until size)

            if (percolation.isFull(row, column)) {
                percolation.open(row, column)
                percolation.printGrid()
                //Thread.sleep(500)
                percolates = percolation.percolates()
                loops++
            }
        }
        results[i] = loops
    }

    println(results.average().div(size * size))

}