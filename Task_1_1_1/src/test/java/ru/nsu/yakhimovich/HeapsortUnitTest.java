package ru.nsu.yakhimovich;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Юнит тесты для проверки сортировки.
 */
public class HeapsortUnitTest {
    @Test
    void heapsortOneElemTest() {
        System.out.println("===== TEST 1 =====");
        int[] ans = Heapsort.heapsort(new int[]{1});
        Assertions.assertArrayEquals(new int[]{1}, ans);
    }

    @Test
    void heapsortBigArrayTest() {
        System.out.println("===== TEST 2 =====");
        int[] sizes = {2000000, 3000000, 5000000};
        long[] times = new long[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            int length = sizes[i];
            int[] sortArray = new int[length];

            Random random = new Random();
            for (int j = 0; j < length; j++) { // Создание массива из случайных чисел
                sortArray[j] = random.nextInt(100);
            }

            long startTime = System.nanoTime(); // Замер времени
            int[] ans = Heapsort.heapsort(sortArray);
            long endTime = System.nanoTime(); // Замер времени
            times[i] = endTime - startTime;
            // Сверяю с сортировкой из библиотеки, ведь не могу предопределить результат
            Arrays.sort(sortArray);
            Assertions.assertArrayEquals(sortArray, ans);
            System.out.printf("Время для массива длины %d = %d мс%n", length, times[i] / 1000000);
        }

        // Рассчет разницы во времени, на практике непостоянна, так как массив из случайных чисел
        double log2Before = Math.log(sizes[0]) / Math.log(2); // Так как log в Math с основанием e
        double log2After = Math.log(sizes[1]) / Math.log(2); // Так как log в Math с основанием e
        double expectedTimeIncrease = (sizes[1] * log2After) / (sizes[0] * log2Before);
        double actualTimeIncrease = (double) times[1] / times[0];
        System.out.printf("Ожидаемое увеличение времени = %f%n", expectedTimeIncrease);
        System.out.printf("Реальное увеличение времени = %f%n", actualTimeIncrease);
    }

    @Test
    void heapsortEmptyTest() {
        System.out.println("===== TEST 3 =====");
        Assertions.assertArrayEquals(new int[]{},
                Heapsort.heapsort(new int[]{}));
    }

    @Test
    void heapsortIsCorrectTest() {
        System.out.println("===== TEST 4 =====");
        int[] ans = Heapsort.heapsort(new int[]{0, 148, -137, -177, 2, 23});
        Assertions.assertArrayEquals(new int[]{-177, -137, 0, 2, 23, 148}, ans);
    }
}