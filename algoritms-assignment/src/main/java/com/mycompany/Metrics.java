package com.mycompany;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Класс для сбора метрик: время, глубина рекурсии, счетчики операций.
 */
public class Metrics {

    // Метрики для текущего запуска
    private static long comparisons = 0;
    private static long allocations = 0;
    private static int currentDepth = 0;
    private static int maxDepth = 0;
    private static long startTime = 0;
    private static long endTime = 0;

    /** Начинает отсчет времени и сбрасывает счетчики. */
    public static void start() {
        comparisons = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTime = System.nanoTime();
    }

    /** Останавливает отсчет времени. */
    public static void stop() {
        endTime = System.nanoTime();
    }

    /** Инкрементирует счетчик сравнений. */
    public static void incrementComparisons(long count) {
        comparisons += count;
    }
    
    /** Инкрементирует счетчик сравнений на 1. */
    public static void incrementComparisons() {
        comparisons++;
    }

    /** Инкрементирует счетчик аллокаций. */
    public static void incrementAllocations(long count) {
        allocations += count;
    }
    
    /** Инкрементирует счетчик аллокаций на 1. */
    public static void incrementAllocations() {
        allocations++;
    }

    /** Обертка для отслеживания глубины рекурсии. */
    public static <T> T trackDepth(Callable<T> recursiveCall) throws Exception {
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);
        try {
            // Здесь выполняется рекурсивный вызов, переданный в качестве аргумента
            return recursiveCall.call();
        } finally {
            // Важно: всегда уменьшаем глубину, даже если произошла ошибка
            currentDepth--;
        }
    }

    /** Возвращает максимальную глубину рекурсии. */
    public static int getMaxDepth() {
        return maxDepth;
    }

    /** Возвращает время выполнения в миллисекундах. */
    public static double getTimeMs() {
        // Мы используем 1_000_000.0, чтобы перейти от наносекунд к миллисекундам.
        return (endTime - startTime) / 1_000_000.0;
    }
    
    /** Запись результатов в CSV. */
    public static void writeCsvLine(String algoName, int n) {
        // Этот метод будет записывать результат в файл metrics.csv
        try (FileWriter writer = new FileWriter("metrics.csv", true)) {
            // Заголовок (пишется только один раз, если файл пуст)
            if (new java.io.File("metrics.csv").length() == 0) {
                 writer.append("Algorithm,N,Time_ms,Max_Depth,Comparisons,Allocations\n");
            }
            
            String line = String.format("%s,%d,%.3f,%d,%d,%d\n",
                algoName, n, getTimeMs(), maxDepth, comparisons, allocations);
            writer.append(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}