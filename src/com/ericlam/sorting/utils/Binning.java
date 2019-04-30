package com.ericlam.sorting.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Binning {
    public static double widthPart(Collection<Double> ints,int size){
        double min = Collections.min(ints);
        double max = Collections.max(ints);
        return (max - min) / size;
    }

    public static double widthPart(double[] ints,int size){
        List<Double> doubles = new ArrayList<>();
        for (double v : ints) {
            doubles.add(v);
        }
        return widthPart(doubles,size);
    }

    public static double widthPart(int[] ints,int size){
        List<Double> doubles = new ArrayList<>();
        for (double v : ints) {
            doubles.add(v);
        }
        return widthPart(doubles,size);
    }

}
