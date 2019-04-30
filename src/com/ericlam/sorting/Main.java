package com.ericlam.sorting;

import com.ericlam.sorting.sortfunc.Histogram;
import com.ericlam.sorting.sortfunc.QuantileSort;

public class Main {
    public static void main(String[] args) {
        int[] test = {85, 60, 89, 97, 98, 94, 83, 69, 89, 94, 90, 78, 88, 102, 82, 110, 90, 90, 80, 68, 89, 94, 81, 65, 102, 86, 84, 89, 92, 72, 82, 94, 81, 80, 91, 85, 86, 103, 85, 93,
                82, 60, 81, 94, 98, 79, 83, 86, 75, 86, 94, 85, 90, 64, 105, 96, 82, 95, 99, 82, 102, 77, 75, 77, 77, 98, 87, 73, 73, 85, 76, 94, 73, 60, 99, 106, 94, 89, 79, 112, 94,
                82, 75, 86, 79, 82, 92, 94, 84, 93, 60, 82, 71, 89, 110, 74, 98, 96, 106};
        double[] q1 = {1,3,5,7,9,10};
        double[] q2 = {2,4,6,8,10};
    }

    public static void QuantilePlot(int[] test){
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

    public static void Histogram(int[] test){
        Histogram his = new Histogram(test);
        his.printGraph();
    }
}
