import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Юнит тесты.
 */
public class UnitTests {
    @Test
    void test1() {
        // Можно проверить самостоятельно
        ArrayList<Integer> realAnswer = new ArrayList<> (
            Arrays.asList(0, 24, 62, 86)
        );

        try {
            ArrayList<Integer> answer = (ArrayList<Integer>)Substring.find(
                "src/test/resources/test1.txt",
                "tryapochka"
            );
            Assertions.assertEquals(realAnswer, answer);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    void test2() {
        // Файл генерировался случайно, в строку было вставлено ровно 20 искомых подстрок,
        // первое вхождение фиксированно и равно 50167 код выдаёт ровно 20 результатов
        //  и первое вхождение совпадает, остальные позиции должны совпадать также
        ArrayList<Integer> realAnswer = new ArrayList<> (
            Arrays.asList(
                50167,
                83535,
                89173,
                98033,
                125762,
                127065,
                134338,
                205825,
                231022,
                246787,
                273330,
                275783,
                298414,
                340605,
                361834,
                413770,
                416938,
                444749,
                459704,
                476995
            )
        );

        try {
            ArrayList<Integer> answer = (ArrayList<Integer>) Substring.find(
                "src/test/resources/test2.txt",
                "tryapochka"
            );
            Assertions.assertEquals(realAnswer, answer);
        } catch (Exception ex) {
            fail();
        }
    }
}