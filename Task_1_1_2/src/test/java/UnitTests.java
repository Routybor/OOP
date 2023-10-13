import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitTests {
    @Test
    void testToString() {
        Polynomial p1 = new Polynomial(new int[]{13, 3, 7});
        Assertions.assertEquals("7x^2 + 3x + 13", p1.toString());
    }

    @Test
    void testBase() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Assertions.assertEquals("7x^3 + 6x^2 + 19x + 6", p1.plus(p2.differentiate(1)).toString());
        Assertions.assertEquals(3510, p1.times(p2).evaluate(2));
    }

    @Test
    void testPlus() {
        Polynomial p1 = new Polynomial(new int[]{});
        Polynomial p2 = new Polynomial(new int[]{63, 1, 22});
        Assertions.assertEquals("22x^2 + 1x + 63", p1.plus(p2).toString());
    }

    @Test
    void testTimes() {
        Polynomial p1 = new Polynomial(new int[]{});
        Polynomial p2 = new Polynomial(new int[]{1, 2, 2});
        Assertions.assertEquals("0", p1.times(p2).toString());
    }

    @Test
    void testMinus() {
        Polynomial p1 = new Polynomial(new int[]{3, 1, 3});
        Polynomial p2 = new Polynomial(new int[]{12, -9, 23});
        Assertions.assertEquals("20x^2 + 10x - 9", p1.minus(p2).toString());
    }

    @Test
    void testEvaluate() {
        Polynomial p1 = new Polynomial(new int[]{10, -2, 3, 11, -5});
        Assertions.assertEquals(-142, p1.evaluate(-2));
    }

    @Test
    void testDifferentiate() {
        Polynomial p1 = new Polynomial(new int[]{16, 0, -13, 1, 0});
        Assertions.assertEquals("3x^3 - 26x^2", p1.differentiate(1).toString());
    }

    @Test
    void testEquals() {
        Polynomial p1 = new Polynomial(new int[]{10, 9, 6});
        Polynomial p2 = new Polynomial(new int[]{10, 6, 9});
        Polynomial p3 = new Polynomial(new int[]{10, 9, 6});
        Assertions.assertFalse(p1.equals(p2) && !p1.equals(p3));
    }
}