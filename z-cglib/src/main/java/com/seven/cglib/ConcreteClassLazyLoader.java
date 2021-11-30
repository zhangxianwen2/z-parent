package com.seven.cglib;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.LazyLoader;

/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/4/2 14:19
 **/
public class ConcreteClassLazyLoader implements LazyLoader {
    @Override
    public Object loadObject() throws Exception {
        System.out.println("before LazyLoader...");
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("LazyLoader");
        propertyBean.setValue("LazyLoader");
        System.out.println("after LazyLoader...");
        return propertyBean;
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        ListNode listNode = reverseNode2(node);
        System.out.println();
    }

    // 1 -> 2 -> 3 -> 4 -> 5
    private static ListNode reverseNode(ListNode node) {

        if (node == null || node.next == null) {
            return node;
        }

        ListNode curr = node;
        ListNode prev = null;
        while (curr != null) {
            ListNode tmp = curr;
            curr = curr.next;
            tmp.next = prev;
            prev = tmp;
        }
        return prev;
    }

    private static ListNode reverseNode2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseNode2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }


    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
