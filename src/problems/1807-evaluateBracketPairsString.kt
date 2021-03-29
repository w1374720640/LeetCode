package problems

/**
 * 1807. 替换字符串中的括号内容 https://leetcode-cn.com/problems/evaluate-the-bracket-pairs-of-a-string/
 *
 * 解：先处理knowledge数组，将所有键值对转换成HashMap中的键值对，方便查找
 * 因为括号成对出现且不会出现嵌套，分别使用两个整数记录左括号和右括号的索引
 * 以括号中间的内容为键，在HashMap中查找对应的值或'?'符号，
 * 所有的值用StringBuilder拼接成新的字符串
 */
fun evaluateBracketPairsString(s: String, knowledge: List<List<String>>): String {
    val map = HashMap<String, String>()
    knowledge.forEach { list ->
        map[list[0]] = list[1]
    }
    val stringBuilder = StringBuilder()
    var i = -1
    for (j in s.indices) {
        val char = s[j]
        if (char == '(') {
            if (j - i > 1) {
                stringBuilder.append(s.substring(i + 1, j))
            }
            i = j
        } else if (char == ')') {
            val key = s.substring(i + 1, j)
            val value = map[key]
            if (value == null) {
                stringBuilder.append('?')
            } else {
                stringBuilder.append(value)
            }
            i = j
        }
    }
    if (s.length - i > 1) {
        stringBuilder.append(s.substring(i + 1))
    }
    return stringBuilder.toString()
}

private fun arrayToList(array: Array<Array<String>>): List<List<String>> {
    val list = ArrayList<List<String>>(array.size)
    array.forEach {
        val subList = ArrayList<String>()
        subList.add(it[0])
        subList.add(it[1])
        list.add(subList)
    }
    return list
}

private fun test(origin: String, array: Array<Array<String>>, expect: String) {
    check(evaluateBracketPairsString(origin, arrayToList(array)) == expect)
}

fun main() {
    test(
        "(name)is(age)yearsold",
        arrayOf(arrayOf("name", "bob"), arrayOf("age", "two")),
        "bobistwoyearsold"
    )
    test(
        "hi(name)",
        arrayOf(arrayOf("a", "b")),
        "hi?"
    )
    test(
        "(a)(a)(a)aaa",
        arrayOf(arrayOf("a", "yes")),
        "yesyesyesaaa"
    )
    test(
        "(a)(b)",
        arrayOf(arrayOf("a", "b"), arrayOf("b", "a")),
        "ba"
    )
    test(
        "",
        arrayOf(arrayOf("a", "b")),
        ""
    )
    println("check succeed.")
}