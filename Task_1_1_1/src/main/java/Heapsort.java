public class Heapsort {
    public static void main(String[] args) { /* Нужно для возможности отдельного запуска решения */
        System.out.println("This is my heapsort");
    }
    /**
     * Соровка массива с использованием сортировки кучей.
     *
     * @param arr Массив, который необходимо отсортировать.
     * @return Отсортированный массив.
     */
    static int[] heapsort(int[] arr) {
        int index = arr.length - 1;
        int i;
        int swap;

        for (i = index / 2; i >= 0; i--) { // построение кучи для всего массива
            heapify(arr, index, i);
        }
        for (i = index; i > 0; i--) { // перебор элементов из кучи
            swap = arr[0]; // перемещение корня в конеч
            arr[0] = arr[i];
            arr[i] = swap;
            heapify(arr, i, 0); // построение кучи для части массива
        }

        return arr;
    }
    /**
     * Преобразует поддерево, заданное в массиве, в двоичную кучу.
     *
     * @param arr Массив, содержащий поддерево для преобразования в кучу.
     * @param n   Размер поддерева.
     * @param i   Индекс корня поддерева.
     */
    public static void heapify(int[] arr, int n, int i) {
        int largest = i; // наибольший берётся как корень
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        int swap;

        if (rightChild < n && arr[rightChild] > arr[largest]) { // смена наибольшего в зависимости от дочерних
            largest = rightChild;
        }
        if (leftChild < n && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }
        if (largest != i) { // если корень не наибольший
            swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest); // рекурсия по поддереву
        }
    }
}
