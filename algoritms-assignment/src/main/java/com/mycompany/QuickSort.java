package com.mycompany;

import java.util.concurrent.Callable;

public class QuickSort {

    private static final int CUTOFF = 16;

    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        Metrics.start();
        try {
            sort(a, 0, a.length - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Metrics.stop();
        Metrics.writeCsvLine("QuickSort", a.length);
    }

    private static void sort(int[] a, int lo, int hi) throws Exception {
        if (hi <= lo) return;

        if (hi - lo < CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }
        
        // Медиана из трех: выбор лучшего опорного элемента
        int m = medianOf3(a, lo, lo + (hi - lo) / 2, hi);
        swap(a, lo, m); 

        // 3-way Partition: возвращает индексы lt и gt
        int lt = lo;
        int gt = hi;
        int i = lo + 1;
        int v = a[lo]; // Опорный элемент

        while (i <= gt) {
            Metrics.incrementComparisons(); 
            if (a[i] < v) {
                swap(a, lt++, i++);
            } else if (a[i] > v) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        
        // Рекурсия с отслеживанием глубины
        final int leftLt = lt;
        final int rightGt = gt;
        Metrics.trackDepth(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sort(a, lo, leftLt - 1);
                return null;
            }
        });
        Metrics.trackDepth(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sort(a, rightGt + 1, hi);
                return null;
            }
        });
    }

    // Вспомогательная функция для Insertion Sort (для отсечения)
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

    // Вспомогательная функция для обмена элементов
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        Metrics.incrementAllocations(3); 
    }

    // Вспомогательная функция для выбора медианы из трех
    private static int medianOf3(int[] a, int i, int j, int k) {
        Metrics.incrementComparisons(3); 
        if (a[i] < a[j]) {
            if (a[j] < a[k]) return j;
            if (a[i] < a[k]) return k;
            return i;
        } else {
            if (a[k] < a[j]) return j;
            if (a[k] < a[i]) return k;
            return i;
        }
    }
}