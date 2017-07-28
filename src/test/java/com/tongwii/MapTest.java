package com.tongwii;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * TreeMap测试
 *
 * @author Zeral
 * @date 2017-07-27
 */
public class MapTest {

    @Test
    public void test1() {
        Map<String, String> contactMap = new TreeMap<>();
        contactMap.put("H", "test");
        contactMap.put("F", "zzz");
        contactMap.put("B", "ttt");
        contactMap.put("A", "dfadsf");
        for (String string : contactMap.keySet()) {
            System.out.printf(string);
        }
    }
}
