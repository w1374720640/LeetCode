package problems

/**
 * 乐团站位 https://leetcode-cn.com/problems/SNJvJP/
 * 某乐团的演出场地可视作 num * num 的二维矩阵 grid（左上角坐标为 [0,0])，每个位置站有一位成员。
 * 乐团共有 9 种乐器，乐器编号为 1~9，每位成员持有 1 个乐器。
 * 为保证声乐混合效果，成员站位规则为：自 grid 左上角开始顺时针螺旋形向内循环以 1，2，...，9 循环重复排列。
 * 例如当 num = 5 时，站位如图所示
 * 请返回位于场地坐标 [Xpos,Ypos] 的成员所持乐器编号。
 *
 * 示例 1：
 * 输入：num = 3, Xpos = 0, Ypos = 2
 * 输出：3
 *
 * 示例 2：
 * 输入：num = 4, Xpos = 1, Ypos = 2
 * 输出：5
 *
 * 提示：
 * 1 <= num <= 10^9
 * 0 <= Xpos, Ypos < num
 *
 * 解：因为场地是正方形的，找到给定点所在的正方形，设该正方形距离场地边界最短距离为k
 * 若想从左上角到达指定位置，需要先遍历完外层的所有点，再从该正方形坐上角遍历到给定点
 * 外层点的总数：上下方数量：num*k*2，加上左右侧数量：(num-2*k)*k*2
 * 计算从正方形左上角到达给定点的距离时，先判断点在哪条边上（按上、右、下、左的顺序判断）
 * 再计算从起点到给定点的距离
 * 最后将外层点的总数加上正方形上的总数减一与9取余再加一就是当前成员的乐器编号
 */
fun orchestraLayout(num: Int, xPos: Int, yPos: Int): Int {
    val k = minOf(minOf(xPos, num - 1 - xPos), minOf(yPos, num - 1 - yPos))
    var total = 0L
    if (k > 0) {
        // 前后两部分都需要先强转成Long再计算
        total = num.toLong() * k * 2 + (num.toLong() - 2 * k) * k * 2
    }
    val x = xPos - k
    val y = yPos - k
    val size = num - k - k
    // 这里total增加的数量为实际数量减一，例如左上角的点total值加0而不是1
    when {
        x == 0 -> total += y
        y == size - 1 -> total += size - 1 + x
        x == size - 1 -> total += (size.toLong() - 1) * 2 + (size - 1 - y) // 前半部分需要先强转Long类型再计算
        else -> total += (size.toLong() - 1) * 3 + (size - 1 - x) // 前半部分需要先强转Long类型再计算
    }
    return (total % 9 + 1).toInt()
}

private fun test(num: Int, xPos: Int, yPos: Int, expect: Int) {
    check(orchestraLayout(num, xPos, yPos) == expect)
}

fun main() {
    test(3, 0, 2, 3)
    test(4, 1, 2, 5)
    test(5, 2, 2, 7)
    test(5, 0, 0, 1)
    test(5, 0, 3, 4)
    test(5, 1, 3, 1)
    test(5, 2, 3, 2)
    test(5, 3, 2, 4)
    test(5, 2, 1, 6)
    test(5, 1, 4, 6)
    test(5, 4, 2, 2)
    test(5, 4, 0, 4)
    test(5, 3, 0, 5)
    test(5, 1, 0, 7)
    // 整型溢出测试
    test(971131546,966980466, 531910024, 3)
    println("check succeed.")
}