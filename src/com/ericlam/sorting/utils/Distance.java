package com.ericlam.sorting.utils;

import java.util.Map;

public class Distance {
    public static double euclidean(Map<Double,Double> map){
        double result = 0;
        for (double k : map.keySet()) {
            double v = map.get(k);
            result += Math.pow(k-v,2);
        }
        return Math.sqrt(result);
    }


    public static double manhanttan(Map<Double,Double> map){
        double result = 0;
        for (double k : map.keySet()) {
            double v = map.get(k);
            result += Math.round(k-v);
        }
        return result;
    }
}
