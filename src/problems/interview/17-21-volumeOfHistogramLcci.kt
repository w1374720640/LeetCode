package problems.interview

import java.util.*
import kotlin.collections.HashMap

/**
 * 面试题 17.21.直方图的水量 https://leetcode-cn.com/problems/volume-of-histogram-lcci/
 *
 * 解：先求所有黑色柱状图和水的总面积，再减去黑色柱状图的面积就是水的总面积
 * 由示意图可知，水位从最高的柱子两侧开始逐渐减小，
 * 以每个柱子的高度为key，索引列表（因为可能由多个柱子的高度相同）为value，放入HashMap中，时间复杂度O(N)，空间复杂度O(N)
 * 创建一个优先队列，将柱子的高度放入最大优先队列中，时间O(NlgN)，空间O(N)
 * 从优先队列中取出最大的两个柱子，计算围成的面积，同时记录左右边界，
 * 继续从优先队列中取出柱子，判断是否在左右边界外，如果在边界外，计算和边界围成的面积，如果在边界内则忽略，
 * 不断从优先队列中取出柱子重复上述过程，直到队列为空或取出的柱子高度为0，时间O(N)
 * 将总面积减去所有柱子的面积就是水的面积，时间O(N)
 * 总的时间复杂度为O(NlgN)，空间复杂度为O(N)
 */
fun trap(height: IntArray): Int {
    if (height.size < 3) return 0
    val map = HashMap<Int, LinkedList<Int>>()
    for (i in height.indices) {
        val list = map[height[i]] ?: LinkedList()
        list.add(i)
        map[height[i]] = list
    }

    // 最大的排在最前面
    val descComparator = kotlin.Comparator<Int> { o1, o2 ->
        o1.compareTo(o2) * -1
    }
    val pq = PriorityQueue<Int>(descComparator)
    for (i in height.indices) {
        pq.add(height[i])
    }

    var area = 0
    var left: Int
    var right: Int

    val max = pq.poll()
    val next = pq.poll()
    left = map[max]!!.pollFirst()
    right = map[next]!!.pollFirst()
    if (left > right) {
        val temp = left
        left = right
        right = temp
    }
    area = max + (right - left) * next

    while (pq.isNotEmpty()) {
        val value = pq.poll()
        if (value == 0) break
        val index = map[value]!!.pollFirst()
        if (index < left) {
            area += (left - index) * value
            left = index
        } else if (index > right) {
            area += (index - right) * value
            right = index
        }
    }

    for (i in height.indices) {
        area -= height[i]
    }
    return area
}

/**
 * 动态规划
 * 参考：https://leetcode-cn.com/problems/volume-of-histogram-lcci/solution/zhi-fang-tu-de-shui-liang-by-leetcode-so-7rla/
 * 时间复杂度O(N)，空间复杂度O(N)
 */
fun trap2(height: IntArray): Int {
    if (height.size < 3) return 0
    val leftMax = IntArray(height.size)
    var max = 0
    for (i in height.indices) {
        max = maxOf(max, height[i])
        leftMax[i] = max
    }
    val rightMax = IntArray(height.size)
    max = 0
    for (i in height.size - 1 downTo 0) {
        max = maxOf(max, height[i])
        rightMax[i] = max
    }

    var area = 0
    for (i in height.indices) {
        area += minOf(leftMax[i], rightMax[i]) - height[i]
    }
    return area
}

/**
 * 单调栈
 * 参考链接同上，时间复杂度O(N)，空间复杂度O(N)
 */
fun trap3(height: IntArray): Int {
    val stack = Stack<Int>()
    var area = 0
    for (i in height.indices) {
        while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
            val top = stack.pop()
            if (stack.isEmpty()) break
            val left = stack.peek()
            area += (i - left - 1) * (minOf(height[left], height[i]) - height[top])
        }
        stack.push(i)
    }
    return area
}

/**
 * 双指针
 * 使用两个指针替代leftMax数组和rightMax数组，空间复杂度为O(1)
 */
fun trap4(height: IntArray): Int {
    if (height.size < 3) return 0
    var area = 0
    var left = 0
    var right = height.size - 1
    var leftMax = 0
    var rightMax = 0
    while (left < right) {
        leftMax = maxOf(leftMax, height[left])
        rightMax = maxOf(rightMax, height[right])
        if (height[left] < height[right]) {
            area += leftMax - height[left]
            left++
        } else {
            area += rightMax - height[right]
            right--
        }
    }
    return area
}

private fun test(trapFun: (IntArray) -> Int) {
    check(trapFun(intArrayOf()) == 0)
    check(trapFun(intArrayOf(3)) == 0)
    check(trapFun(intArrayOf(1, 2)) == 0)
    check(trapFun(intArrayOf(1, 2, 3)) == 0)
    check(trapFun(intArrayOf(3, 2, 1)) == 0)
    check(trapFun(intArrayOf(2, 1, 3)) == 1)
    check(trapFun(intArrayOf(2, 0, 3)) == 2)
    check(trapFun(intArrayOf(0, 1, 2)) == 0)
    check(trapFun(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)) == 6)
    check(trapFun(intArrayOf(5, 0, 4, 1, 2)) == 5)
    check(trapFun(intArrayOf(4, 5, 5, 5, 4)) == 0)
    check(trapFun(intArrayOf(4, 5, 5, 5, 1, 2)) == 1)
    check(trapFun(intArrayOf(4, 5, 0, 0, 5, 2, 5)) == 13)
}

fun main() {
    test(::trap)
    test(::trap2)
    test(::trap3)
    test(::trap4)
    println("check succeed.")
}