package app.luxion.nexus.navigation.sections

import app.luxion.nexus.theme.WidthClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BentoGridTest {

    @Test
    fun cellCountEqualsItemCountForEveryWidth() {
        WidthClass.entries.forEach { widthClass ->
            (0..14).forEach { count ->
                assertEquals(count, bentoCells(count, widthClass).size, "$widthClass with $count items")
            }
        }
    }

    @Test
    fun gridHasNoEmptyOrOverlappingCells() {
        WidthClass.entries.forEach { widthClass ->
            val columns = bentoColumnCount(widthClass)
            (1..14).forEach { count ->
                val cells = bentoCells(count, widthClass)
                val occupied = cells.flatMap { cell ->
                    (cell.row until cell.row + cell.rowSpan).flatMap { row ->
                        (cell.column until cell.column + cell.columnSpan).map { column -> row to column }
                    }
                }
                val rows = cells.maxOf { it.row + it.rowSpan }
                assertEquals(occupied.size, occupied.toSet().size, "overlap for $widthClass with $count items")
                assertEquals(rows * columns, occupied.size, "empty cells for $widthClass with $count items")
                assertTrue(cells.all { it.column + it.columnSpan <= columns })
            }
        }
    }

    @Test
    fun sixItemsAtExpandedFollowTheDesignedRhythm() {
        assertEquals(
            listOf(
                BentoCell(column = 0, row = 0, columnSpan = 2, rowSpan = 2),
                BentoCell(column = 2, row = 0, columnSpan = 1, rowSpan = 2),
                BentoCell(column = 0, row = 2, columnSpan = 1, rowSpan = 1),
                BentoCell(column = 1, row = 2, columnSpan = 1, rowSpan = 1),
                BentoCell(column = 2, row = 2, columnSpan = 1, rowSpan = 1),
                BentoCell(column = 0, row = 3, columnSpan = 3, rowSpan = 1),
            ),
            bentoCells(6, WidthClass.Expanded),
        )
    }
}
