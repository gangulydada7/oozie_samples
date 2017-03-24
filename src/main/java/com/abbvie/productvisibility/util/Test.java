package com.abbvie.productvisibility.util;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * Created by selvakx1 on 11/2/2016.
 */
public class Test {

    public static void main(String[] args) {
        NavigableMap<String, Integer> navigableMap = new TreeMap<String, Integer>();
        String[] letters = { "a", "b", "c" };
        int[] ints = { 3, 2, 1 };
        for (int i = 0; i < letters.length; i++){
            navigableMap.put(letters[i], ints[i]);
        }
        System.out.println("Map = " + navigableMap);
        NavigableSet<String> ns = navigableMap.navigableKeySet();

        Iterator iter = ns.iterator();
        while (iter.hasNext()){
            System.out.print(iter.next() + "HH"+" ");
        }
        System.out.println();
        ns = navigableMap.descendingKeySet();
        iter = ns.iterator();
        while (iter.hasNext()){
            System.out.print(iter.next() + " ");
        }

        for(String i : navigableMap.navigableKeySet())
        {
            System.out.println("Hello "+navigableMap.get(i));
        }

        System.out.println();
        System.out.println("First entry = " + navigableMap.firstEntry());
        System.out.println("Last entry = " + navigableMap.lastEntry());
        System.out.println("Entry < a is " + navigableMap.lowerEntry("a"));
        System.out.println("Entry > c is " + navigableMap.higherEntry("c"));
        System.out.println("Poll first entry: " + navigableMap.pollFirstEntry());
        System.out.println("Map = " + navigableMap);
        System.out.println("Poll last entry: " + navigableMap.pollLastEntry());
        System.out.println("Map = " + navigableMap);
    }
}
