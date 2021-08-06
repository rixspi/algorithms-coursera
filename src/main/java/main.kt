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

    val windowSize = 600.dp
    val gridSize = 1000
    val boxSize = (windowSize - 40.dp).div(gridSize.toDouble().dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = windowSize, height = windowSize)
    ) {
        val percolation = remember { PercolationState(gridSize) }

        Canvas(modifier = Modifier.fillMaxSize()) {
            percolation.grid.forEachIndexed { row, it ->
                it.forEachIndexed { column, site ->
                    val offset = Offset(column * boxSize, row * boxSize)
                    val size = Size(boxSize, boxSize)
                    if (site.open) {
                        drawRect(Color.White, topLeft = offset, size = size)
                    } else {
                        drawRect(Color.Black, topLeft = offset, size = size)
                    }
                }
            }
        }
        LaunchedEffect(Unit) {

            while (true) {
                withFrameMillis {
                    if (percolation.percolates().not()) {
                        percolation.update()
                    }
                }
            }
        }
    }
}
