package problems

import base.ListNode
import base.createListNode
import base.toIntArray

/**
 * 61. 旋转链表 https://leetcode-cn.com/problems/rotate-list/
 * 给你一个链表的头节点head，旋转链表，将链表每个节点向右移动k个位置。
 *
 * 解：旋转链表操作等价于将链表最后k个元素删除，并拼接到链表头部，
 * 对于长度小于k的链表，k等于k除以size的余数
 * 从头节点开始遍历，记录节点总数、倒数第k+1个节点和最后一个节点，
 * 到链表结尾时，若节点总数大于k，使用临时变量记录倒数k+1个节点的下一个节点，作为返回值，
 * 将k+1个节点的next指针置空，最后一个节点的next指针指向head，返回保存的临时节点，
 * 若节点总数等于k，则直接返回head节点，
 * 若节点总数小于k，对k重新赋值为k除以size的余数，从头重新遍历
 */
fun rotateRight(head: ListNode?, k: Int): ListNode? {
    if (k == 0 || head == null) return head
    var i = k
    var size = 1
    var lastK1: ListNode = head
    var last: ListNode = head
    while (true) {
        val next = last.next
        if (next == null) {
            when {
                size == i -> return head
                size > i -> {
                    val temp = lastK1.next!!
                    lastK1.next = null
                    last.next = head
                    return temp
                }
                else -> {
                    i = i % size
                    // 这里一开始没有考虑到，可以直接返回
                    if (i == 0) return head
                    size = 1
                    lastK1 = head
                    last = head
                }
            }
        } else {
            size++
            last = next
            if (size - i > 1) {
                lastK1 = lastK1.next!!
            }
        }
    }
}

private fun checkRotateRight(input: IntArray, k: Int, expect: IntArray) {
    check(rotateRight(input.createListNode(), k).toIntArray().contentEquals(expect))
}

fun main() {
    checkRotateRight(intArrayOf(1, 2, 3, 4, 5), 2, intArrayOf(4, 5, 1, 2, 3))
    checkRotateRight(intArrayOf(0, 1, 2), 1, intArrayOf(2, 0, 1))
    checkRotateRight(intArrayOf(0, 1, 2), 2, intArrayOf(1, 2, 0))
    checkRotateRight(intArrayOf(0, 1, 2), 3, intArrayOf(0, 1, 2))
    checkRotateRight(intArrayOf(0, 1, 2), 4, intArrayOf(2, 0, 1))
    checkRotateRight(intArrayOf(1), 2, intArrayOf(1))
    println("check succeed.")
}