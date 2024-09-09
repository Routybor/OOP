package ru.nsu.yakhimovich;

import java.util.Scanner;
/**
 * Реализация пирамидальной сортировки.
 */
public class Heapsort {
    private static int iterationCount1 = 0;
    private static int iterationCount2 = 0;

    public static void main(String[] args) {
        System.out.println("===== HEAPSORT INITIATED =====");
        Scanner scan = new Scanner(System.in);
        System.out.print("Размер массива = ");
        int size = scan.nextInt();
        int[] arr = new int[size];
        System.out.print("Перечислите элементы = ");
        for (int i = 0; i < size; i++) {
            arr[i] = scan.nextInt();
        }
        heapsort(arr);
        System.out.print("Результат = ");
        for (int i : arr) {
            System.out.printf("%d ", i);
        }
    }

    /**
     * Соровка массива с использованием сортировки кучей.
     *
     * @param arr Массив, который необходимо отсортировать.
     * @return Отсортированный массив.
     */
    static int[] heapsort(int[] arr) {
        iterationCount1 = 0;
        iterationCount2 = 0;
        int index = arr.length - 1;

        if (index != -1) { // Пустой массив
            for (int i = index / 2; i >= 0; i--) {
                heapify(arr, index, i, 0);
            }

            for (int i = index; i > 0; i--) {
                int swap = arr[0];
                arr[0] = arr[i];
                arr[i] = swap;
                heapify(arr, i, 0, 1);
            }
        } else {
            index = 0;
        }
        int expectedIterations = (int) (index * (Math.log(index) / Math.log(2)));
        System.out.printf("Всего итераций:     %d + %d = %d%n", iterationCount1, iterationCount2,
                                                              iterationCount1 + iterationCount2);
        System.out.printf("Ожидаемо итераций : %d%n", expectedIterations);
        return arr;
    }

    /**
     * Преобразует поддерево, заданное в массиве, в двоичную кучу.
     *
     * @param arr Массив, содержащий поддерево для преобразования в кучу.
     * @param n   Размер поддерева.
     * @param i   Индекс корня поддерева.
     * @param loop Номер цикла, который вызывает функцию.
     */
    public static void heapify(int[] arr, int n, int i, int loop) {
        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;

        if (loop == 0) {
            iterationCount1++;
        } else {
            iterationCount2++;
        }

        if (leftChild < n && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }
        if (rightChild < n && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest, loop);
        }
    }
}
