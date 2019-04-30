package com.ericlam.sorting.spreading.mining;

import com.ericlam.sorting.api.DataMiner;
import com.ericlam.sorting.utils.AdvMath;

import java.util.ArrayList;
import java.util.List;

public class Association extends DataMiner {

    public Association(double[][] tables, double confidence, double support) {
        super(tables, confidence, support);
    }

    public Association(List<double[]> tables, double confidence, double support) {
        super(tables, confidence, support);
    }

    public boolean implement(double[] IF, double[] THEN){
        double conf = getConfidence(IF,THEN);
        double supp = getSupport(IF,THEN);
        return conf >= getMinConfidence() && supp >= getMinSupport();
    }

    public double getConfidence(double[] IF, double[] THEN){
        List<Integer> contain = new ArrayList<>();
        for (int i = 0; i < tables.length; i++) {
            double[] row = tables[i];
            if (AdvMath.contain(row,IF)) contain.add(i); // get the getConfidence total
        }

        int total = contain.size();
        int con = 0;
        for (int i : contain) {
            double[] row = tables[i];
            if (AdvMath.contain(row,THEN)) ++con;
        }
        return (double)con / total;
    }

    public double getSupport(double[] IF, double[] THEN){
        int total = tables.length;
        int contain = 0;
        for (double[] row : tables) {
            if (AdvMath.contain(row,IF) && AdvMath.contain(row,THEN)) ++contain;
        }
        return (double)contain / total;
    }

    public long getRuleCount(){
        return (long)Math.pow(2,tables.length) - 2;
    }
}
