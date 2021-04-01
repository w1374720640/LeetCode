package problems


/**
 * 1006.笨阶乘 https://leetcode-cn.com/problems/clumsy-factorial/
 *
 * 解：以N=10为例，clumsy(10) = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1
 *                            = (10 * 9 / 8) + (7) + (-6 * 5 / 4) + (3) + (-2 * 1)
 * 以每个括号内的值为单位，不停累加即可得出最终值
 */
fun clumsy(N: Int): Int {
    require(N >= 0)
    var result = 0
    var i = 0
    while (i < N) {
        val pair = getValue(N, i)
        result += pair.first
        // 需要在这里更新索引
        i = pair.second
    }
    return result
}

private fun getValue(N: Int, i: Int): Pair<Int, Int> {
    require(i >= 0)
    var value = N - i
    if (i % 4 == 3) return value to i + 1
    check(i % 4 == 0)
    if (i < N - 1) {
        value *= N - i - 1
        if (i < N - 2) {
            value /= N - i - 2
        }
    }
    return if (i == 0) {
        // 只有i==0时返回正数，其他都返回负数
        value to 3
    } else {
        value * -1 to i + 3
    }
}

fun main() {
    check(clumsy(0) == 0)
    check(clumsy(1) == 1)
    check(clumsy(2) == 2)
    check(clumsy(3) == 6)
    check(clumsy(4) == 7)
    check(clumsy(10) == 12)
    println("check succeed.")
}