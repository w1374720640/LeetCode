package problems

/**
 * 187. 重复的DNA序列 https://leetcode-cn.com/problems/repeated-dna-sequences/
 *
 * 解：创建两个HashSet，
 * 从起点开始遍历原始字符，每次截取10个字符，如果第一个Set中不存在，则存放到第一个Set中，否则存放到第二个Set中
 * 最后将第二个Set转换为List即可，
 * 时间复杂度O(N)，空间复杂度O(N)
 */
fun findRepeatedDnaSequences(s: String): List<String> {
    val firstSet = HashSet<String>()
    val secondSet = HashSet<String>()
    for (i in 0..s.length - 10) {
        val key = s.substring(i, i + 10)
        if (firstSet.contains(key)) {
            secondSet.add(key)
        } else {
            firstSet.add(key)
        }
    }
    return secondSet.toList()
}

/**
 * 根据Rabin-Karp算法对Hash函数进行优化，让Hash函数可以在常数时间内完成（无论是截取10字符还是100字符）
 * 设需要计算的字符为R进制，定义hash函数为 var h=0;for(i in s.indices) h=h*R+i;return h;
 * 对于长度为M的子字符串，当需要计算下一个字符串的hash值时，
 * 需要将原hash值减去第一个字符乘以R的M-1次方，再将结果乘R，再加上下一个字符的值就是下一个字符串的hash值（数学上可以证明公式的正确性）
 * 可以参考《算法（第四版》这本书和我的另一个仓库中的完整代码实现：
 * https://github.com/w1374720640/Algorithms-4th-Edition-in-Kotlin/blob/master/src/chapter5/section3/RabinKarp.kt
 * 这里ACGT只有四种，所以R=4，M=10，和完整实现相比，没有对大素数取余，
 * 仍然使用两个HashSet，不过第一个HashSet存放Int值，
 * 时间复杂度和空间复杂度仍然是O(N)，但是系数变小
 */
fun findRepeatedDnaSequences2(s: String): List<String> {
    if (s.length <= 10) return emptyList()
    val map = hashMapOf<Char, Int>('A' to 0, 'C' to 1, 'G' to 2, 'T' to 3)
    val R = 4
    // R的M-1次方
    var RM = 1
    repeat(9) {
        RM *= R
    }
    val firstSet = HashSet<Int>()
    val secondSet = HashSet<String>()

    var hash = 0
    // 计算第一组子字符串的Hash值
    repeat(10) {
        hash = hash * R + map[s[it]]!!
    }
    firstSet.add(hash)
    for (i in 1..s.length - 10) {
        // 计算去除第一个元素的hash值
        hash -= map[s[i - 1]]!! * RM
        // 计算添加后一个元素后的hash值
        hash = hash * R + map[s[i + 9]]!!
        if (firstSet.contains(hash)) {
            secondSet.add(s.substring(i, i + 10))
        } else {
            firstSet.add(hash)
        }
    }
    return secondSet.toList()
}

/**
 * 通过位运算来确定hash值
 * ACGT共有四个数，用两位二进制表示分别为00 01 10 11
 * 计算hash值时，将原值左移两位，分别和四个二进制数或操作，得到新的hash值
 * 子字符串长度固定为10，每个字母对应的二进制占两位，取前12为为0，后20为为1的整数作为掩码，与新的hash值与操作，得到最终的hash值
 * 不同排列的子字符串hash值必定不同
 */
fun findRepeatedDnaSequences3(s: String): List<String> {
    if (s.length <= 10) return emptyList()
    val map = hashMapOf<Char, Int>('A' to 0, 'C' to 1, 'G' to 2, 'T' to 3)
    val bitmask = -1 ushr 12 // -1的二进制位全部为1，无符号右移12位，则前12位为0，后12位为1
    val firstSet = HashSet<Int>()
    val secondSet = HashSet<String>()

    var hash = 0
    // 计算第一组子字符串的Hash值
    repeat(10) {
        hash = (hash shl 2) or map[s[it]]!!
    }
    firstSet.add(hash and bitmask)
    for (i in 1..s.length - 10) {
        hash = ((hash shl 2) or map[s[i + 9]]!!) and bitmask
        if (firstSet.contains(hash)) {
            secondSet.add(s.substring(i, i + 10))
        } else {
            firstSet.add(hash)
        }
    }
    return secondSet.toList()
}


private fun test(s: String, array: Array<String>) {
    val set1 = findRepeatedDnaSequences(s).toSet()
    val set2 = array.toHashSet()
    check(set1.size == set2.size)
    set1.forEach {
        check(set2.contains(it))
    }
}

fun main() {
    test("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT", arrayOf("AAAAACCCCC", "CCCCCAAAAA"))
    test("AAAAAAAAAAA", arrayOf("AAAAAAAAAA"))
    test("AAAAAAAAAA", arrayOf())
    test("ACAGT", arrayOf())
    test("AAAACCCCCGGGGGTTTTT", arrayOf())
    println("check succeed.")
}