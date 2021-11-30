package com.seven.arithmetic;

import java.util.LinkedHashMap;

/**
 * @author zhangxianwen
 * @date 2021/7/21 21:45
 **/
public class LRUCacheTest {

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(4));
        System.out.println();

        //

        LinkedHashMap<Integer,Integer> map = new LinkedHashMap(2);
        map.put(1, 1); // 缓存是 {1=1}
        map.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(map.get(1));
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(map.get(2));
        map.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(map.get(1));
        System.out.println(map.get(3));
        System.out.println(map.get(4));
        System.out.println();
    }

}
