package com.ericlam.sorting.sortfunc;

import java.util.*;

public class QuantileSort {
    public enum Type{

        Q1(0.25),
        Q2(0.50),
        Q3(0.75),
        Q4(1.0);

        private double percent;

        Type(double percent){
            this.percent = percent;
        }
    }

    private LinkedList<Integer> list = new LinkedList<>();

    public QuantileSort(int[] ints){
        for (int i : ints) {
            list.add(i);
        }
        Collections.sort(list);
    }

    public QuantileSort(Collection<Integer> ints){
        list.addAll(ints);
        Collections.sort(list);
    }

    public int getQuantileNum(Type type){
       return list.get((int)((list.size()-1)*type.percent));
    }

    public int getQuantileRange(Type t1,Type t2){
        return getQuantileNum(t2) - getQuantileNum(t1);
    }

    public List<Integer> getQuantile(Type type,boolean treed){
        int start = (int)((list.size()-1)*(type.percent-0.25));
        int end = (int)((list.size()-1)*(type.percent));
        if (!treed) return list.subList(start,end);
        else{
            SortedSet<Integer> result = new TreeSet<>();
            int i = 0;
            for (int tree : list) {
                if (i < start) {
                    i++;
                    continue;
                }
                if (i > end) break;
                result.add(tree);
                i++;
            }
            return new LinkedList<>(result);
        }
    }

    public List<Integer> getList() {
        return list;
    }

    public TreeSet<Integer> getTree(){
        return new TreeSet<>(list);
    }

    public void printGraph1(boolean treed){
        List<Integer> list = treed ? new LinkedList<>(new TreeSet<>(this.list)) : new LinkedList<>(this.list);
        for (int tree : list) {
            System.out.print("█".repeat(tree)+" :"+tree);
            checkQ(tree);
        }
    }

    private void checkQ(int tree) {
        for (Type type : Type.values()) {
            if (tree == getQuantileNum(type)) {
                System.out.print(" - "+type.toString());
                break;
            }
        }
        System.out.println();
    }

    public void printGraph2(boolean treed){
        List<Integer> list = treed ? new LinkedList<>(new TreeSet<>(this.list)) : new LinkedList<>(this.list);
        for (int tree : list) {
            System.out.print(" ".repeat(tree)+"█ :"+tree);
            checkQ(tree);
        }
    }
}
