import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
public class UnitTest {
    @Test
    void heapsort_one_elem_test() {
        System.out.println("===== TEST 1 =====");
        int[] ans = Heapsort.heapsort(new int[]{1});
        Assertions.assertArrayEquals(new int[]{1}, ans);
    }

    @Test
    void heapsort_big_array_test() {
        System.out.println("===== TEST 2 =====");
        int[] sizes = {2_000_000, 16_000_000};
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
            Arrays.sort(sortArray); // Сверяю с сортировкой из библиотеки, ведь не могу предопределить результат
            Assertions.assertArrayEquals(sortArray, ans);
            System.out.printf("Time to sort array of length = %d = %d ms%n", length, times[i] / 1_000_000);
        }

        // Рассчет разницы во времени, на практике непостоянна, так как массив из случайных чисел
        double log2Before = Math.log(sizes[0]) / Math.log(2); // Так как log в Math с основанием e
        double log2After = Math.log(sizes[1]) / Math.log(2); // Так как log в Math с основанием e
        double expectedTimeIncrease = (sizes[1] * log2After) / (sizes[0] * log2Before);
        double actualTimeIncrease = (double) times[1] / times[0];
        System.out.printf("Expected time increase = %f%n", expectedTimeIncrease);
        System.out.printf("Actual time increase = %f%n", actualTimeIncrease);
    }

    @Test
    void heapsort_empty_test() {
        System.out.println("===== TEST 3 =====");
        Assertions.assertArrayEquals(new int[]{},
                Heapsort.heapsort(new int[]{}));
    }

    @Test
    void heapsort__is_correct_test() {
        System.out.println("===== TEST 4 =====");
        int[] ans = Heapsort.heapsort(new int[]{0, 148, -137, -177, 2, 23});
        Assertions.assertArrayEquals(new int[]{-177, -137, 0, 2, 23, 148}, ans);
    }
}