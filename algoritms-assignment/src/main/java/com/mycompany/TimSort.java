package com.mycompany;

import java.util.Arrays;

public class TimSort {

    /**
     * Использует встроенную сортировку Java (TimSort) и измеряет время.
     * Мы не можем измерить сравнения/глубину, так как это код за пределами JVM.
     * @param a Массив для сортировки
     */
    public static void sort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        
        Metrics.start();
        
        // Использование встроенной гибридной сортировки Java (основана на TimSort)
        Arrays.sort(a); 
        
        Metrics.stop();
        
        // Запись результата в CSV
        Metrics.writeCsvLine("TimSort", a.length);
    }
}
