package com.mycompany.algo;

import java.util.Arrays;
import java.util.Random;

import com.mycompany.MergeSort;
import com.mycompany.Metrics;
import com.mycompany.QuickSort;
import com.mycompany.TimSort;

public class Main {

    private static final int[] N_SIZES = {5000, 10000, 20000, 50000, 100000};
    private static final int REPEATS = 5;

    public static void main(String[] args) {
        System.out.println("Starting data collection. Results will be saved to metrics.csv");

        for (int N : N_SIZES) {
            System.out.println("\n--- Testing N = " + N + " ---");
            for (int repeat = 0; repeat < REPEATS; repeat++) {
                
                runTests(N, "Random");
                
                runTests(N, "Sorted");
                
                runTests(N, "Reverse");
            }
        }
        System.out.println("\nData collection complete. Check metrics.csv.");
    }

    private static void runTests(int N, String type) {
        int[] originalArray = generateArray(N, type);

        int[] arr1 = Arrays.copyOf(originalArray, N);
        MergeSort.sort(arr1);
        System.out.printf(" %s | %d | MergeSort done.\n", type, N);

        int[] arr2 = Arrays.copyOf(originalArray, N);
        QuickSort.sort(arr2);
        System.out.printf(" %s | %d | QuickSort done.\n", type, N);
        
        int[] arr3 = Arrays.copyOf(originalArray, N);
        TimSort.sort(arr3);
        System.out.printf(" %s | %d | TimSort done.\n", type, N);
    }
    
    private static int[] generateArray(int N, String type) {
        Random random = new Random();
        int[] arr = new int[N];
        
        if ("Random".equals(type)) {
            for (int i = 0; i < N; i++) {
                arr[i] = random.nextInt(N);
            }
        } else if ("Sorted".equals(type)) {
            for (int i = 0; i < N; i++) {
                arr[i] = i;
            }
        } else if ("Reverse".equals(type)) {
            for (int i = 0; i < N; i++) {
                arr[i] = N - i - 1;
            }
        }
        Metrics.incrementAllocations(N);
        return arr;
    }
}