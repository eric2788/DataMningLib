package com.ericlam.sorting.container;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ValueMapSort {
    public static <K,V extends Comparable> TreeMap<K, V> sort(SortedMap<K,V> map){
        Comparator<K> comparator = (k1,k2)-> {
            int compare =  map.get(k1).compareTo(map.get(k2));
            if (compare == 0){
                return 1;
            }else{
                return compare;
            }
        };
        TreeMap<K,V> sortedMap = new TreeMap<>(comparator);
        sortedMap.putAll(map);
        return sortedMap;
    }
}
