package com.ericlam.sorting;

import com.ericlam.sorting.sortfunc.Histogram;
import com.ericlam.sorting.sortfunc.QuantileSort;
import com.ericlam.sorting.spreading.mining.Association;
import com.ericlam.sorting.utils.AdvMath;
import com.ericlam.sorting.utils.Distance;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author r Eric Lam
 * @apiNote for Data Mining Testing
 */
public class Main {
    public static void main(String[] args) {
        kMeanTesting();
    }

    private static void kMeanTesting() { // K means with manhattan distance testing
        double[][] a = {
                {3, 1},
                {3, 3},
                {2, 4},
                {4, 4},
                {6, 5},
                {7, 8},
                {8, 5},
                {10, 9}
        };
        double[] k1 = {3, 3};
        double[] k2 = {7, 7};
        Set<Integer> p1 = new TreeSet<>();
        Set<Integer> p2 = new TreeSet<>();
        kMeans(a, k1, k2, p1, p2);
    }

    private static List<Set<Integer>> getKSet(double[][] a, double[] k1, double[] k2) {
        Set<Integer> k1Set = new TreeSet<>();
        Set<Integer> k2Set = new TreeSet<>();
        for (int i = 0; i < a.length; i++) {
            double[] arr = a[i];
            double gap1 = Distance.manhanttan(arr, k1);
            double gap2 = Distance.manhanttan(arr, k2);
            if (gap1 < gap2) {
                k1Set.add(i);
            } else if (gap1 > gap2) {
                k2Set.add(i);
            }
        }
        System.out.println("K1 :" + k1Set);
        System.out.println("K2 :" + k2Set);
        return List.of(k1Set, k2Set);
    }

    private static void kMeans(double[][] a, double[] k1, double[] k2, Set<Integer> p1, Set<Integer> p2) {
        List<Set<Integer>> sets = getKSet(a, k1, k2);
        Set<Integer> k1Set = sets.get(0);
        Set<Integer> k2Set = sets.get(1);
        double k1newX, k1newY, k2newX, k2newY;
        double sumX = 0, sumY = 0;
        for (int i : k1Set) {
            sumX += a[i][0];
            sumY += a[i][1];
        }
        k1newX = sumX / k1Set.size();
        k1newY = sumY / k1Set.size();
        sumX = sumY = 0;
        for (int i : k2Set) {
            sumX += a[i][0];
            sumY += a[i][1];
        }
        k2newX = sumX / k2Set.size();
        k2newY = sumY / k2Set.size();
        k1 = new double[]{k1newX, k1newY};
        k2 = new double[]{k2newX, k2newY};
        if (!p1.equals(k1Set) && !p2.equals(k2Set)) {
            kMeans(a, k1, k2, k1Set, k2Set);
        } else {
            System.out.println("Final K1 :" + Arrays.toString(k1));
            System.out.println("Final K2 :" + Arrays.toString(k2));
        }
    }

    private static void classingTest() { //Gini / Entropy / Gain Testing
        List[] list = new List[]{
                List.of('T', 'F', 'P'),
                List.of('F', 'T', 'N'),
                List.of('T', 'T', 'P'),
                List.of('F', 'F', 'N'),
                List.of('T', 'T', 'P'),
                List.of('F', 'F', 'N'),
                List.of('F', 'T', 'N'),
                List.of('F', 'F', 'N'),
                List.of('T', 'T', 'N'),
                List.of('F', 'F', 'N')
        };
        List<List> p = Arrays.stream(list).filter(l -> l.contains('P')).collect(Collectors.toList());
        List<List> n = Arrays.stream(list).filter(l -> l.contains('N')).collect(Collectors.toList());
        System.out.println(p);
        System.out.println(n);
        long pXT = p.stream().filter(l -> (char) l.get(0) == 'T').count();
        long pYT = p.stream().filter(l -> (char) l.get(1) == 'T').count();
        long pXF = p.stream().filter(l -> (char) l.get(0) == 'F').count();
        long pYF = p.stream().filter(l -> (char) l.get(1) == 'F').count();
        long nXT = n.stream().filter(l -> (char) l.get(0) == 'T').count();
        long nYT = n.stream().filter(l -> (char) l.get(1) == 'T').count();
        long nXF = n.stream().filter(l -> (char) l.get(0) == 'F').count();
        long nYF = n.stream().filter(l -> (char) l.get(1) == 'F').count();
        int pSize = p.size();
        int nSize = n.size();
        int total = list.length;
        double overall = AdvMath.entropy(total, pSize, nSize);
        // for X
        double EXT = AdvMath.entropy(pXT + nXT, pXT, nXT);
        double EXF = AdvMath.entropy(pXF + nXF, pXF, nXF);
        // for Y
        double EYT = AdvMath.entropy(pYT + nYT, pYT, nYT);
        double EYF = AdvMath.entropy(pYF + nYF, pYF, nYF);
        System.out.println("Overall entropy: " + overall);
        System.out.println("Ex=T: " + EXT);
        System.out.println("Ex=F: " + EXF);
        //System.out.println(overall+" - "+(pXT+nXT)+" / "+total+" x "+EXT+" - "+(pXF+nXF)+" / "+total+" x "+EXF);
        double sumEX = overall - (((double) (pXT + nXT) / total) * EXT) - (((double) (pXF + nXF) / total) * EXF);
        System.out.println("Sum of X: " + AdvMath.round(4, sumEX));

        System.out.println("Ey=T: " + EYT);
        System.out.println("Ex=T: " + EYF);
        double sumEY = overall - (((double) (pYT + nYT) / total) * EYT) - (((double) (pYF + nYF) / total) * EYF);
        System.out.println("Sum of Y: " + AdvMath.round(4, sumEY));
        // for X
        double GXT = AdvMath.gini(pXT + nXT, pXT, nXT);
        double GXF = AdvMath.gini(pXF + nXF, pXF, nXF);
        // for Y
        double GYT = AdvMath.gini(pYT + nYT, pYT, nYT);
        double GYF = AdvMath.gini(pYF + nYF, pYF, nYF);
        System.out.println("Gx=T: " + GXT);
        System.out.println("Gx=F: " + GXF);
        double sumGX = (((double) (pXT + nXT) / total) * GXT) + (((double) (pXF + nXF) / total) * GXF);
        System.out.println("Sum of X: " + AdvMath.round(3, sumGX));
        System.out.println("Gy=T: " + GYT);
        System.out.println("Gy=F: " + GYF);
        double sumGY = (((double) (pYT + nYT) / total) * GYT) + (((double) (pYF + nYF) / total) * GYF);
        System.out.println("Sum of Y: " + AdvMath.round(3, sumGY));
    }

    private static void testAssRules() { // Association rules testing
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
