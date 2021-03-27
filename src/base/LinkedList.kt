package base

/**
 * 单向链表结点
 */
class ListNode(var `val`: Int, var next: ListNode? = null)


fun IntArray.createListNode(): ListNode? {
    if (isEmpty()) return null
    val first = ListNode(this[0])
    var node = first
    for (i in 1 until size) {
        val next = ListNode(this[i])
        node.next = next
        node = next
    }
    return first
}

fun ListNode?.toIntArray(): IntArray {
    if (this == null) return intArrayOf()
    val list = ArrayList<Int>()
    list.add(this.`val`)
    var next = this.next
    while (next != null) {
        list.add(next.`val`)
        next = next.next
    }
    return list.toIntArray()
}