package problems

/**
 * 190. 颠倒二进制位 https://leetcode-cn.com/problems/reverse-bits/
 *
 * 解：如何判断第i位是0还是1：将1左移i-1位，与原值进行与操作，结果非0则第i位是1，等于0则第i位是0
 * 如何设置第j位的值，将1左移j-1位，与待修改的值进行或操作
 */
fun reverseBits(n: Int): Int {
    var result = 0
    for (i in 0..31) {
        if (n and (1 shl i) != 0) {
            result = result or (1 shl (31 - i))
        }
    }
    return result
}

fun main() {
    check(reverseBits(43261596) == 964176192)
    check(reverseBits(-3) == -1073741825)
    check(reverseBits(0) == 0)
    check(reverseBits(Int.MIN_VALUE) == 1)
    check(reverseBits(Int.MAX_VALUE) == -2)
    println("check succeed.")
}