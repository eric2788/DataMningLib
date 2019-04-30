package com.ericlam.sorting.api;

import com.ericlam.sorting.utils.AdvMath;

import java.util.List;

public abstract class DataMiner {
    protected double[][] tables;
    private double confidence;
    private double support;

    public DataMiner(double[][] tables, double confidence, double  support) {
        this.tables = tables;
        this.confidence = confidence > 1 ? confidence /Math.pow(10, AdvMath.getNumLenght(confidence)) :  confidence;
        this.support = support > 1 ? support / Math.pow(10,AdvMath.getNumLenght(support)) : support;
    }

    public DataMiner(List<double[]> tables, double confidence, double support){
        this.tables = new double[tables.size()][];
        for (int i = 0; i < tables.size(); i++) {
            double[] inside = tables.get(i);
            this.tables[i] = inside;
        }
        this.confidence = confidence > 1 ? Math.pow(10,AdvMath.getNumLenght(confidence)) :  confidence;
        this.support = support > 1 ? Math.pow(10,AdvMath.getNumLenght(support)) : support;
    }

    public double getMinConfidence() {
        return confidence;
    }

    public double getMinSupport() {
        return support;
    }
}
