package com.ericlam.sorting.utils;

public class Distance {
    public static double euclidean(double[] i, double[] j) {
        double result = 0;
        int length = Math.min(i.length, j.length);
        for (int k = 0; k < length; k++) {
            result += Math.pow(i[k] - j[k], 2);
        }
        return Math.sqrt(result);
    }


    public static double manhanttan(double[] i, double[] j) {
        double result = 0;
        int length = Math.min(i.length, j.length);
        for (int k = 0; k < length; k++) {
            result += Math.abs(i[k] - j[k]);
        }
        return result;
    }

    public static double minkowski(double[] i, double[] j, int sqr) {
        double result = 0;
        int length = Math.min(i.length, j.length);
        for (int k = 0; k < length; k++) {
            result += Math.pow(Math.abs(i[k] - j[k]), sqr);
        }
        return sqr * Math.sqrt(result);
    }

    public static double sumpremum(double[] i, double[] j) {
        int length = Math.min(i.length, j.length);
        double result = 0;
        for (int k = 0; k < length; k++) {
            result = Math.max(Math.abs(i[k] - j[k]), result);
        }
        return result;
    }
}
