package problems

/**
 * 1805. 字符串中不同整数的数目 https://leetcode-cn.com/problems/number-of-different-integers-in-a-string/
 *
 * 解：遍历String，找到所有的数字，加入HashSet中，最后统计Set的大小就是不同整数的数量
 * 需要注意数字可能超过Int的大小，可以用String（需要手动去除前导0）或BitInteger代替
 */
fun numDifferentIntegers(word: String): Int {
    val set = HashSet<String>()
    var i = -1
    var j = 0
    while (true) {
        if (j < word.length && word[j] in '0'..'9') {
            if (i == -1) {
                i = j
            }
        } else {
            if (i != -1) {
                val numString = word.substring(i, j)
                var k = 0
                while (k < numString.length && numString[k] == '0') {
                    k++
                }
                set.add(if (k < numString.length) numString.substring(k) else "")
                i = -1
            }
            if (j == word.length) break
        }
        j++
    }
    return set.size
}

fun main() {
    check(numDifferentIntegers("a123bc34d8ef34") == 3)
    check(numDifferentIntegers("leet1234code234") == 2)
    check(numDifferentIntegers("a1b01c001") == 1)
    check(numDifferentIntegers("167278959591294") == 1)
    println("check succeed.")
}