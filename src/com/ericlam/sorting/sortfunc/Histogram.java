package com.ericlam.sorting.sortfunc;

import com.ericlam.sorting.container.ValueMapSort;

import java.util.*;


public class Histogram {
    private LinkedList<Integer> list = new LinkedList<>();
    private TreeMap<Double,Integer> frequentMap = new TreeMap<>();
    private TreeMap<Double,Integer> resultMap;
    private final double gap;

    public Histogram(int[] ints){
        for (int i : ints) {
            list.add(i);
        }
        Collections.sort(list);
        int max = getTree().last();
        int min = getTree().first();
        gap = (double)((max - min) / 10);
        structMap(min,max,gap);
    }

    public Histogram(Collection<Integer> ints){
        list.addAll(ints);
        Collections.sort(list);
        int max = getTree().last();
        int min = getTree().first();
        gap = (double)((max - min) / 10);
        structMap(min,max,gap);
    }

    private void structMap(int min,int max,double gap){
        System.out.println("max: "+max+" ; min: "+min+" ; gap: "+gap);
        for (double i = min; i < max; i += gap) {
            frequentMap.putIfAbsent(i,1);
        }
        for (int integer : list) {
            double first = frequentMap.firstKey();
            for (double key : frequentMap.keySet()) {
                if (integer >= first && integer <= key){
                    frequentMap.computeIfPresent(key,(k,v)->++v);
                    break;
                }
                first = key;
            }
        }
        System.out.println("Before Sort: "+frequentMap);
        resultMap = ValueMapSort.sort(frequentMap);
        System.out.println("After Sort: "+resultMap);
    }


    public LinkedList<Integer> getList() {
        return list;
    }

    public TreeMap<Double, Integer> getFrequentMap() {
        return frequentMap;
    }

    public TreeSet<Integer> getTree(){
        return new TreeSet<>(list);
    }

    public void printGraph(){ // Not finished
        Map.Entry<Double,Integer> postOrder = resultMap.lastEntry();
        ArrayList<Integer> repeat = new ArrayList<>();
        int prev = 0;
        for (Integer value : resultMap.values()) {
            switch (Integer.compare(prev,value)){
                case 0:
                case 1:
                    repeat.add(value);
                    break;
                case -1:
                    repeat.add(0,value);
                    break;
            }
            prev = value;
        }
        print(repeat);
    }

    private void print(ArrayList<Integer> list){
        for (int i = 0; i < list.size(); i++) {
            int dup = list.get(i);
            System.out.println("█".repeat(dup));
        }
    }
}
