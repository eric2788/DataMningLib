package com.ericlam.sorting;

import com.ericlam.sorting.sortfunc.Histogram;
import com.ericlam.sorting.sortfunc.QuantileSort;
import com.ericlam.sorting.spreading.mining.Association;
import com.ericlam.sorting.utils.AdvMath;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String[] row1 = {"Bread", "Milk"};
        String[] row2 = {"Bread", "Diaper", "Beer", "Eggs"};
        String[] row3 = {"Milk", "Diaper", "Beer", "Cake"};
        String[] row4 = {"Bread", "Milk", "Diaper", "Beer"};
        String[] row5 = {"Bread", "Milk", "Diaper", "Cake"};

        String[] if1 = {"Milk", "Diaper"};
        String[] then1 = {"Beer"};

        String[] if2 = {"Beer", "Bread"};
        String[] then2 = {"Milk"};

        double[][] tables = new double[][]{
                AdvMath.toHashDoubleArray(row1),
                AdvMath.toHashDoubleArray(row2),
                AdvMath.toHashDoubleArray(row3),
                AdvMath.toHashDoubleArray(row4),
                AdvMath.toHashDoubleArray(row5),
        };

        Association ass = new Association(tables, 0.5, 0.4);
        checkAssociation(ass, if1, then1);
        checkAssociation(ass, if2, then2);

    }

    private static void checkAssociation(Association ass, Object[] if1, Object[] then1) {
        double conf1 = ass.getConfidence(AdvMath.toHashDoubleArray(if1), AdvMath.toHashDoubleArray(then1));
        double sup1 = ass.getSupport(AdvMath.toHashDoubleArray(if1), AdvMath.toHashDoubleArray(then1));
        System.out.println(Arrays.toString(if1) + " => " + Arrays.toString(then1));
        System.out.println("Conf: " + AdvMath.round(2, conf1));
        System.out.println("Sup: " + AdvMath.round(2, sup1));
        System.out.println("Passed: " + ass.implement(AdvMath.toHashDoubleArray(if1), AdvMath.toHashDoubleArray(then1)));
    }

    private static void QuantilePlot(int[] test) {
        QuantileSort sort = new QuantileSort(test);
        System.out.println("After Sorted:"+sort.getList());
        for (QuantileSort.Type type : QuantileSort.Type.values()) {
            System.out.println(type.toString()+": "+sort.getQuantile(type,true));
        }
        for (QuantileSort.Type type : QuantileSort.Type.values()) {
            System.out.println("Inter Quantile of "+type.toString()+": "+sort.getQuantileNum(type));
        }
        System.out.println("Inter Quantile Range: "+sort.getQuantileRange(QuantileSort.Type.Q1, QuantileSort.Type.Q3));
        System.out.println("=====================");
        sort.printGraph1(true);
    }

    private static void Histogram(int[] test) {
        Histogram his = new Histogram(test);
        his.printGraph();
    }
}
