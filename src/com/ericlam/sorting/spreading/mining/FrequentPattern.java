package com.ericlam.sorting.spreading.mining;

import com.ericlam.sorting.api.DataMiner;
import com.ericlam.sorting.utils.AdvMath;

import java.util.*;
import java.util.stream.Collectors;

public class FrequentPattern extends DataMiner {
    private Set<Double> values = new TreeSet<>();

    public FrequentPattern(double[][] tables, double confidence, double support) {
        super(tables, confidence, support);
        for (double[] table : tables) {
            for (double v : table) {
                values.add(v);
            }
        }
    }

    public FrequentPattern(List<double[]> tables, double confidence, double support) {
        super(tables, confidence, support);
        for (double[] table : tables) {
            for (double v : table) {
                values.add(v);
            }
        }
    }

    private void construct(){ //Fucked Up
        Map<Double,Double> duplicates = new TreeMap<>();
        final int total = tables.length;
        for (double value : values) {
            long count = AdvMath.count(tables,new double[]{value});
            duplicates.put(value,(double)count/total);
        }
        Set<Double> tie1 = duplicates.keySet().stream().filter(d->duplicates.get(d) >= getMinSupport()).collect(Collectors.toSet());
    }

}
