package com.mycompany;

import java.util.concurrent.Callable;

public class MergeSort {

    private static final int CUTOFF = 16; 

    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        Metrics.start();
        
        int[] aux = new int[a.length];
        Metrics.incrementAllocations(a.length); 

        try {
            sort(a, aux, 0, a.length - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Metrics.stop();
        Metrics.writeCsvLine("MergeSort", a.length);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) throws Exception {
        if (hi - lo < CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        
        Metrics.trackDepth(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sort(a, aux, lo, mid);
                return null;
            }
        });

        Metrics.trackDepth(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sort(a, aux, mid + 1, hi);
                return null;
            }
        });
        
        merge(a, aux, lo, mid, hi);
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo; j--) {
                Metrics.incrementComparisons(); 
                if (a[j] < a[j - 1]) {
                    swap(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        Metrics.incrementAllocations(3); 
    }


    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
            Metrics.incrementAllocations(); 
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) { 
                a[k] = aux[j++];
            } else if (j > hi) { 
                a[k] = aux[i++];
            } else {
                Metrics.incrementComparisons(); 
                if (aux[j] < aux[i]) {
                    a[k] = aux[j++];
                } else {
                    a[k] = aux[i++];
                }
            }
            Metrics.incrementAllocations(); 
        }
    }
}