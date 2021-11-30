package com.seven.arithmetic;

/**
 * @author zhangxianwen
 * @date 2021/8/11 13:22
 **/
public class MorrisTest {

    public static void main(String[] args) {
        Morris.Node node1 = new Morris.Node(1);
        Morris.Node node2 = new Morris.Node(2);
        Morris.Node node3 = new Morris.Node(3);
        Morris.Node node4 = new Morris.Node(4);
        Morris.Node node5 = new Morris.Node(5);
        Morris.Node node6 = new Morris.Node(6);
        node1.left = node2;
        node1.right = node6;
        node2.left = node3;
        node2.right = node4;
        node4.right = node5;

        Morris.morris(node1);
    }

}
