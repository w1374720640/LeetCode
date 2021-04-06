package problems


/**
 * 采购计划 https://leetcode-cn.com/problems/4xy4Wx/
 * 小力将N个零件的报价存于数组nums。小力预算为target，假定小力仅购买两个零件，要求购买零件的花费不超过预算，请问他有多少种采购方案。
 * 注意：答案需要以 1e9 + 7 (1000000007) 为底取模，如：计算初始结果为：1000000008，请返回 1
 *
 * 示例 1：
 * 输入：nums = [2,5,3,5], target = 6
 * 输出：1
 * 解释：预算内仅能购买 nums[0] 与 nums[2]。
 *
 * 示例 2：
 * 输入：nums = [2,2,1,9], target = 10
 * 输出：4
 * 解释：符合预算的采购方案如下：
 * nums[0] + nums[1] = 4
 * nums[0] + nums[2] = 3
 * nums[1] + nums[2] = 3
 * nums[2] + nums[3] = 10
 *
 * 解：先对数组排序，用两个指针分别从左右两侧向中间遍历，
 * 对于左侧的一个指针i来说，找到右侧第一个和它相加小于等于target的值j，
 * i位置和j右侧任意一个零件都无法组成采购方案，和左侧（包括j）任意一个都能组成采购方案，总数为 j - i
 * i右侧的任意零件都只能和j左侧的零件组成采购方案，
 * 所以i不停向右遍历，j不停向左遍历，直到i、j相遇，遍历数组一次，复杂度O(N)
 */
fun purchasePlans(nums: IntArray, target: Int): Int {
    val Q = 1000000007
    nums.sort()
    var total = 0
    var j = nums.size - 1
    for (i in nums.indices) {
        while (j > i && nums[i] + nums[j] > target) {
            j--
        }
        if (i >= j) break
        total = (total + j - i) % Q
    }
    return total
}

private fun test(array: IntArray, target: Int, expect: Int) {
    check(purchasePlans(array, target) == expect)
}

fun main() {
    test(intArrayOf(2, 5, 3, 5), 6, 1)
    test(intArrayOf(2,2,1,9), 10, 4)
    test(intArrayOf(1, 2, 2, 4, 4, 4), 4, 3)
    test(intArrayOf(1, 2, 2, 4, 4, 4), 7, 12)
    test(intArrayOf(1, 2, 2, 4, 4, 4), 8, 15)
    test(intArrayOf(1, 2, 2, 4, 4, 4), 9, 15)
    test(intArrayOf(1, 2, 2, 4, 4, 4, 7), 8, 16)
    println("check succeed.")
}