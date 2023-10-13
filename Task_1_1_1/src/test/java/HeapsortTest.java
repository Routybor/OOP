import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class HeapsortTest {
    @Test
    void heapsort_one_elem_test() {
        int[] ans = Heapsort.heapsort(new int[] {1});
        Assertions.assertArrayEquals(new int[] {1}, ans);
    }

    @Test
    void heapsort_empty_test() {
        Assertions.assertArrayEquals(new int[] {},
                Heapsort.heapsort(new int[] {}));
    }

    @Test
    void heapsort_test1() {
        int[] ans = Heapsort.heapsort(new int[] {1234, 5, 5, 222, 131311, 0, 1});
        Assertions.assertArrayEquals(new int[] {0, 1, 5, 5, 222, 1234, 131311}, ans);
    }

    @Test
    void heapsort_test2() {
        int[] ans = Heapsort.heapsort(new int[] {0, 148, -137, -137, 2, 23});
        Assertions.assertArrayEquals(new int[] {-137, -137, 0, 2, 23, 148}, ans);
    }
}