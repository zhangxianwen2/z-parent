package com.seven.arithmetic;

/**
 * @author zhangxianwen
 * @date 2021/8/11 13:13
 **/
public class Morris {

    public static void morris(Node node) {
        if (node == null) {
            return;
        }
        Node curr = node;
        Node mostRight;
        while (curr != null) {
            System.out.println(curr.value);
            if (curr.left == null) {
                // 没有左孩子，一定只会遍历一次
                curr = curr.right;
            } else {
                mostRight = curr.left;
                while (mostRight.right != null && mostRight.right != curr) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    // 第一次遍历到最右节点
                    mostRight.right = curr;
                    curr = curr.left;
                } else {
                    // 第二次遍历到最右节点
                    mostRight.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    static class Node {

        int value;
        Node left;
        Node right;

        public Node() {
        }

        Node(int v) {
            this.value = v;
        }

    }

}
