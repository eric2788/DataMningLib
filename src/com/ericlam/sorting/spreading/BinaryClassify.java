package com.ericlam.sorting.spreading;

import com.ericlam.sorting.utils.AdvMath;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryClassify {
    private Object left;
    private Object right;
    private List<Object> zlass;
    private double overall, EXL, EXR, EYL, EYR, sumEX, sumEY, GXL, GXR, GYL, GYR, sumGX, sumGY;

    public BinaryClassify(List[] list, int class_index, int left_index, int right_index) {
        if ((class_index + left_index + right_index) / 3 == class_index || left_index == right_index || class_index == left_index || right_index == class_index) {
            throw new IllegalStateException("Those 3 index  must be unique.");
        }
        boolean pass = true;
        for (List l : list) {
            pass = pass && l.size() == 3;
        }
        if (!pass) throw new IllegalStateException("The column of each row  must be 3");
        zlass = Arrays.stream(list).map(e -> e.get(class_index)).distinct().collect(Collectors.toList());
        if (zlass.size() != 2) throw new IllegalStateException("Class split must be 2");
        List<List> p = Arrays.stream(list).filter(l -> l.contains(zlass.get(0))).collect(Collectors.toList());
        List<List> n = Arrays.stream(list).filter(l -> l.contains(zlass.get(1))).collect(Collectors.toList());
        List<Object> left_variables = Arrays.stream(list).map(l -> l.get(left_index)).distinct().sorted().collect(Collectors.toList());
        List<Object> right_variables = Arrays.stream(list).map(l -> l.get(right_index)).distinct().sorted().collect(Collectors.toList());
        if (!left_variables.equals(right_variables))
            throw new IllegalStateException("Variables between two columns must be same.");
        left = left_variables.get(0);
        right = left_variables.get(1);
        long pXT = p.stream().filter(l -> l.get(0) == left).count();
        long pYT = p.stream().filter(l -> l.get(1) == left).count();
        long pXF = p.stream().filter(l -> l.get(0) == right).count();
        long pYF = p.stream().filter(l -> l.get(1) == right).count();
        long nXT = n.stream().filter(l -> l.get(0) == left).count();
        long nYT = n.stream().filter(l -> l.get(1) == left).count();
        long nXF = n.stream().filter(l -> l.get(0) == right).count();
        long nYF = n.stream().filter(l -> l.get(1) == right).count();
        int pSize = p.size();
        int nSize = n.size();
        int total = list.length;
        overall = AdvMath.entropy(total, pSize, nSize);
        // for X
        EXL = AdvMath.entropy(pXT + nXT, pXT, nXT);
        EXR = AdvMath.entropy(pXF + nXF, pXF, nXF);
        // for Y
        EYL = AdvMath.entropy(pYT + nYT, pYT, nYT);
        EYR = AdvMath.entropy(pYF + nYF, pYF, nYF);

        sumEX = overall - (((double) (pXT + nXT) / total) * EXL) - (((double) (pXF + nXF) / total) * EXR);

        sumEY = overall - (((double) (pYT + nYT) / total) * EYL) - (((double) (pYF + nYF) / total) * EYR);

        // for X
        GXL = AdvMath.gini(pXT + nXT, pXT, nXT);
        GXR = AdvMath.gini(pXF + nXF, pXF, nXF);
        // for Y
        GYL = AdvMath.gini(pYT + nYT, pYT, nYT);
        GYR = AdvMath.gini(pYF + nYF, pYF, nYF);

        sumGX = (((double) (pXT + nXT) / total) * GXL) + (((double) (pXF + nXF) / total) * GXR);

        sumGY = (((double) (pYT + nYT) / total) * GYL) + (((double) (pYF + nYF) / total) * GYR);
    }


    public void printResult() {
        System.out.println("Left: " + left + ", Right: " + right);
        System.out.println("Class: " + zlass);
        System.out.println("====== Entropy ======");
        System.out.println("Overall entropy: " + overall);
        System.out.println("E(X)=" + left + ": " + EXL);
        System.out.println("E(X)=" + right + ": " + EXR);
        System.out.println("EGain(X): " + AdvMath.round(4, sumEX));
        System.out.println("E(Y)=" + left + ": " + EYL);
        System.out.println("E(Y)=" + right + ": " + EYR);
        System.out.println("EGain(Y): " + AdvMath.round(4, sumEY));
        System.out.println("====== Gini Index ======");
        System.out.println("G(X)=" + left + ": " + GXL);
        System.out.println("G(X)=" + right + ": " + GXR);
        System.out.println("GGain(X): " + AdvMath.round(3, sumGX));
        System.out.println("G(Y)=" + left + ": " + GYL);
        System.out.println("G(Y)=" + right + ": " + GYR);
        System.out.println("GGain(Y): " + AdvMath.round(3, sumGY));
    }


}
