package com.ericlam.sorting.spreading;

import com.ericlam.sorting.utils.AdvMath;

public class Normalization {
    private double[] ints;
    private double min;
    private double max;
    private ArrayCalculation cal;

    public Normalization(double... ints) {
        this.ints = ints;
        for (double i : ints) {
            max = Math.max(i,max);
            min = Math.min(i,min);
        }
        cal = new ArrayCalculation(ints);
    }

    public double[] minMaxNormalize(int new_min,int new_max){
        double[] normalized = new double[ints.length];
        for (int i = 0; i < ints.length; i++) {
            double v = ints[i];
            double result = (v - min) / (max - min) * (new_max - new_min) + new_min;
            normalized[i] = AdvMath.round(2,result);
        }
        return normalized;
    }

    public double[] zScoreNormalize(){
        double[] normalized = new double[ints.length];
        for (int i = 0; i < ints.length; i++) {
            double v = ints[i];
            double result = (v - cal.getMean()) / cal.getSd();
            normalized[i] = AdvMath.round(2,result);
        }
        return normalized;
    }

    public double[] decimalScaling(){
        double[] normalized = new double[ints.length];
        for (int i = 0; i < ints.length; i++) {
            double v = ints[i];
            double result = v / Math.pow(10,AdvMath.getNumLenght(max));
            normalized[i] = AdvMath.round(2,result);
        }
        return normalized;
    }


}
