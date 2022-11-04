package com.seven.arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxianwen
 * @date 2021/7/21 21:45
 **/
public class LRUCache {

    int capacity;

    int size;

    Node head;

    Node tail;

    Map<Integer, Node> cache = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key);
        moveToTail(node);
        return node.value;
    }

    void put(int key, int value) {
        if (!cache.containsKey(key)) {
            if (cache.size() == capacity) {
                removeEldest();
                put(key, value);
            } else {
                Node node = new Node(key, value);
                tail.pre.next = node;
                node.next = tail;
                node.pre = tail.pre;
                tail.pre = node;
                cache.put(key, node);
            }
        } else {
            Node node = cache.get(key);
            node.value = value;
            moveToTail(node);
        }
    }

    private void moveToTail(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        //
        tail.pre.next = node;

        node.next = tail;
        node.pre = tail.pre;
        tail.pre = node;
    }

    private void removeEldest() {
        cache.remove(head.next.key);
        head.next = head.next.next;
        head.next.pre = head;
    }

    public int size() {
        return cache.size();
    }

    class whetherBlackTargetsNode {

        int key;
        int value;
        Node pre;
        Node next;

        public Node() {
        }

        Node(int k, int v) {
            this.key = k;
            this.value = v;
        }

    }

}
