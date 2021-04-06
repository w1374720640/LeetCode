package problems

import java.util.*

/**
 * 魔塔游戏 https://leetcode-cn.com/problems/p0NxJO/
 * 小扣当前位于魔塔游戏第一层，共有 N 个房间，编号为 0 ~ N-1。
 * 每个房间的补血道具/怪物对于血量影响记于数组 nums，其中正数表示道具补血数值，即血量增加对应数值；
 * 负数表示怪物造成伤害值，即血量减少对应数值；0 表示房间对血量无影响。
 * 小扣初始血量为 1，且无上限。假定小扣原计划按房间编号升序访问所有房间补血/打怪，为保证血量始终为正值，
 * 小扣需对房间访问顺序进行调整，每次仅能将一个怪物房间（负数的房间）调整至访问顺序末尾。
 * 请返回小扣最少需要调整几次，才能顺利访问所有房间。若调整顺序也无法访问完全部房间，请返回 -1。
 *
 * 示例 1：
 * 输入：nums = [100,100,100,-250,-60,-140,-50,-50,100,150]
 * 输出：1
 * 解释：初始血量为 1。至少需要将 nums[3] 调整至访问顺序末尾以满足要求。
 *
 * 示例 2：
 * 输入：nums = [-200,-300,400,0]
 * 输出：-1
 * 解释：调整访问顺序也无法完成全部房间的访问。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^5 <= nums[i] <= 10^5
 *
 * 解：从左向右对数组遍历，将每个元素相加，
 * 若相加后的总和小于等于0，将左侧最小的元素（必定是负数）调整至末尾（不实际调整，用一个额外变量记录总和），
 * 遍历到最右侧后，将前半段的总和和末尾的总和相加，如果是负数则不存在满足条件的调整方案，如果为正数，返回调整次数
 * 遍历时使用基于小顶堆的优先队列（每次从队列中弹出时会弹出最小的元素）将所有负数加入队列中，调整时弹出最小的负数
 * 时间复杂度O(NlgN)，空间复杂度O(N)
 */
fun magicTower(nums: IntArray): Int {
    val pq = PriorityQueue<Int>()
    var totalLeft = 1L // 可能超过Int范围，默认值为1
    var totalRight = 0L
    var count = 0

    for (i in nums.indices) {
        val value = nums[i]
        if (value < 0) {
            pq.add(value)
        }
        totalLeft += value
        if (totalLeft <= 0) {
            count++
            val min = pq.poll()
            totalLeft -= min
            totalRight += min
        }
    }
    return if (totalLeft + totalRight > 0) count else -1
}

private fun test(array: IntArray, expect: Int) {
    check(magicTower(array) == expect)
}

fun main() {
    test(intArrayOf(100, 100, 100, -250, -60, -140, -50, -50, 100, 150), 1)
    test(intArrayOf(-200, -300, 400, 0), -1)
    test(intArrayOf(-1, -2, -3, 10), 3)
    println("check succeed.")
}