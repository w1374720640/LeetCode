package problems

/**
 * 37. 解数独 https://leetcode-cn.com/problems/sudoku-solver/
 * 编写一个程序，通过填充空格来解决数独问题。
 * 一个数独的解法需遵循如下规则：
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * 空白格用 '.' 表示。
 * 答案被标成红色。
 *
 * 提示：
 * 给定的数独序列只包含数字 1-9 和字符 '.' 。
 * 你可以假设给定的数独只有唯一解。
 * 给定数独永远是 9x9 形式的。
 *
 * 解：用一个整数指定位为0或1表示在某一行、列、方块内是否包含指定数组，例如0b111111010表示不含1和3，而包含其它7个数
 * 创建三个长度为9的整型数组，分别代表行、列、方块内的整数，遍历数组，初始化三个数组中的每个数
 * 将所有空白位置点的x、y坐标加入列表中，使用深度优先搜索的思想遍历列表：
 * 如果该位置只有一种可能，选择那个可能，继续遍历下一个点，
 * 如果该位置有多种可能，选择其中一个，继续遍历下一个点，
 * 如果在遍历过程中发现和规则冲突，回退到上一个点选择另一个可能继续遍历，
 * 直到所有空白点都选择了符合条件的值，在回退过程中对数组赋予相应的字符值
 */
fun solveSudoku(board: Array<CharArray>): Unit {
    val char0 = '0'.toInt()
    val rows = IntArray(9)
    val columns = IntArray(9)
    val cubes = IntArray(9)
    // 空白点
    val points = ArrayList<Pair<Int, Int>>()
    for (i in board.indices) {
        val charArray = board[i]
        for (j in charArray.indices) {
            val char = charArray[j]
            if (char == '.') {
                points.add(i to j)
            } else {
                setValue(rows, columns, cubes, i, j, char.toInt() - char0)
            }
        }
    }

    val result = dfs(board, rows, columns, cubes, points, 0)
    if (!result) throw IllegalStateException("dose not exist.")
}

private fun getCubeIndex(i: Int, j: Int): Int {
    return i / 3 * 3 + j / 3
}

private fun dfs(
    board: Array<CharArray>,
    rows: IntArray,
    columns: IntArray,
    cubes: IntArray,
    list: List<Pair<Int, Int>>,
    index: Int
): Boolean {
    if (index >= list.size) return true
    val point = list[index]
    val x = point.first
    val y = point.second
    val numList = getNumList(rows, columns, cubes, x, y)
    if (numList.isEmpty()) return false
    for (i in numList.indices) {
        val value = numList[i]
        setValue(rows, columns, cubes, x, y, value)
        val result = dfs(board, rows, columns, cubes, list, index + 1)
        if (result) {
            board[x][y] = ('0'.toInt() + value).toChar()
            return true
        } else {
            resetValue(rows, columns, cubes, x, y, value)
        }
    }
    return false
}

private fun getNumList(rows: IntArray, columns: IntArray, cubes: IntArray, i: Int, j: Int): List<Int> {
    val row = rows[i]
    val column = columns[j]
    val cube = cubes[getCubeIndex(i, j)]
    val merge = row or column or cube
    if (merge == 0b111111111) return emptyList()
    val list = ArrayList<Int>()
    var mask = 1
    repeat(9) {
        if (merge and mask == 0) {
            list.add(it + 1)
        }
        mask = mask shl 1
    }
    return list
}

private fun setValue(rows: IntArray, columns: IntArray, cubes: IntArray, i: Int, j: Int, value: Int) {
    val mask = 1 shl (value - 1)
    rows[i] = rows[i] or mask
    columns[j] = columns[j] or mask
    val cubeIndex = getCubeIndex(i, j)
    cubes[cubeIndex] = cubes[cubeIndex] or mask
}

private fun resetValue(rows: IntArray, columns: IntArray, cubes: IntArray, i: Int, j: Int, value: Int) {
    val mask = (1 shl (value - 1)).inv() // 将1左移指定位置后按位取反
    rows[i] = rows[i] and mask
    columns[j] = columns[j] and mask
    val cubeIndex = getCubeIndex(i, j)
    cubes[cubeIndex] = cubes[cubeIndex] and mask
}


fun main() {
    val array = arrayOf(
        charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
        charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
        charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
        charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
        charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
        charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
        charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
        charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
        charArrayOf('.', '.', '.', '.', '8', '.', '.', '7', '9')
    )
    printArray(array)
    solveSudoku(array)
    println()
    printArray(array)
}

private fun printArray(array: Array<CharArray>) {
    println(array.joinToString(separator = "\n") { it.joinToString(separator = " ") })
}