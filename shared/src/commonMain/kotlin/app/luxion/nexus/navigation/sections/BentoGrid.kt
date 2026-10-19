package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import app.luxion.nexus.theme.WidthClass

internal data class BentoCell(val column: Int, val row: Int, val columnSpan: Int, val rowSpan: Int)

internal fun bentoColumnCount(widthClass: WidthClass): Int = when (widthClass) {
    WidthClass.Compact -> 1
    WidthClass.Medium -> 2
    WidthClass.Expanded, WidthClass.Large -> 3
}

internal fun bentoCells(itemCount: Int, widthClass: WidthClass): List<BentoCell> = when (bentoColumnCount(widthClass)) {
    1 -> List(itemCount) { index -> BentoCell(column = 0, row = index, columnSpan = 1, rowSpan = 1) }
    2 -> twoColumnCells(itemCount)
    else -> threeColumnCells(itemCount)
}

private fun twoColumnCells(itemCount: Int): List<BentoCell> {
    val cells = mutableListOf<BentoCell>()
    var row = 0
    var remaining = itemCount
    if (remaining > 0) {
        cells += BentoCell(column = 0, row = row++, columnSpan = 2, rowSpan = 1)
        remaining--
    }
    val trailingWide = remaining % 2 == 1
    val pairedCount = if (trailingWide) remaining - 1 else remaining
    repeat(pairedCount / 2) {
        cells += BentoCell(column = 0, row = row, columnSpan = 1, rowSpan = 1)
        cells += BentoCell(column = 1, row = row, columnSpan = 1, rowSpan = 1)
        row++
    }
    if (trailingWide) cells += BentoCell(column = 0, row = row, columnSpan = 2, rowSpan = 1)
    return cells
}

private fun threeColumnCells(itemCount: Int): List<BentoCell> {
    val cells = mutableListOf<BentoCell>()
    var row = 0
    var remaining = itemCount
    var mirrored = false
    fun feature() {
        val largeColumn = if (mirrored) 1 else 0
        val tallColumn = if (mirrored) 0 else 2
        cells += BentoCell(column = largeColumn, row = row, columnSpan = 2, rowSpan = 2)
        cells += BentoCell(column = tallColumn, row = row, columnSpan = 1, rowSpan = 2)
        row += 2
        remaining -= 2
        mirrored = !mirrored
    }
    fun trio() {
        repeat(3) { column -> cells += BentoCell(column = column, row = row, columnSpan = 1, rowSpan = 1) }
        row++
        remaining -= 3
    }
    fun wide() {
        cells += BentoCell(column = 0, row = row++, columnSpan = 3, rowSpan = 1)
        remaining--
    }
    while (remaining > 0) {
        when (remaining) {
            1 -> wide()
            2, 4 -> feature()
            3 -> trio()
            5 -> {
                feature()
                trio()
            }
            else -> {
                feature()
                trio()
                wide()
            }
        }
    }
    return cells
}

@Composable
internal fun BentoGrid(
    cells: List<BentoCell>,
    columns: Int,
    rowHeight: Dp?,
    spacing: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val spacingPx = spacing.roundToPx()
        val totalWidth = constraints.maxWidth
        val columnWidth = (totalWidth - spacingPx * (columns - 1)) / columns
        val placeableCells = measurables.zip(cells)
        val fixedRowHeight = rowHeight?.roundToPx()
        fun spanWidth(cell: BentoCell) = columnWidth * cell.columnSpan + spacingPx * (cell.columnSpan - 1)
        val placeables = placeableCells.map { (measurable, cell) ->
            val width = spanWidth(cell)
            val constraintsForCell = if (fixedRowHeight != null) {
                val height = fixedRowHeight * cell.rowSpan + spacingPx * (cell.rowSpan - 1)
                Constraints.fixed(width, height)
            } else {
                Constraints(minWidth = width, maxWidth = width)
            }
            measurable.measure(constraintsForCell) to cell
        }
        val rowCount = cells.maxOfOrNull { it.row + it.rowSpan } ?: 0
        val rowHeights = IntArray(rowCount) { row ->
            fixedRowHeight ?: placeables.filter { (_, cell) -> cell.row == row }.maxOfOrNull { it.first.height } ?: 0
        }
        val rowTops = IntArray(rowCount)
        for (row in 1 until rowCount) rowTops[row] = rowTops[row - 1] + rowHeights[row - 1] + spacingPx
        val totalHeight = if (rowCount == 0) 0 else rowTops.last() + rowHeights.last()
        layout(totalWidth, totalHeight) {
            placeables.forEach { (placeable, cell) ->
                placeable.place(x = cell.column * (columnWidth + spacingPx), y = rowTops[cell.row])
            }
        }
    }
}
