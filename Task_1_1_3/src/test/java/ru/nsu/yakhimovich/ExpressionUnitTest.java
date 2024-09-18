package ru.nsu.yakhimovich;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Юнит тесты для проверки методов класса.
 */
public class ExpressionUnitTest {
    Expression expression = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

    @Test
    void printTest1() {
        System.out.println("Печать выражения");
        expression.print();
        Assertions.assertEquals(expression.toString(), "(3+(2*x))");
    }

    @Test
    void derivativeTest() {
        System.out.println("Дифференцирование по переменной x");
        Expression derivative = expression.derivative("x");
        derivative.print();
        Assertions.assertEquals(derivative.toString(), "(0+((0*x)+(2*1)))");
    }

    @Test
    void printTest2() {
        System.out.println("Печать выражения");
        expression = new Sub(new Number(3), new Div(new Number(2), new Variable("x")));
        expression.print();
        Assertions.assertEquals(expression.toString(), "(3-(2/x))");
    }

    @Test
    void evalTest() {
        System.out.println("Вычисление выражения при x = 10");
        int result = expression.eval("x=10; y=13");
        Assertions.assertEquals(result, 23);
    }

    @Test
    void derivativeTwoVarsTest() {
        System.out.println("Дифференцирование дроби");
        expression = new Div(new Add(new Variable("x"), new Number(2)),
                             new Sub(new Variable("x"), new Number(3)));
        Expression derivative = expression.derivative("x");
        derivative.print();
    }

    @Test
    void variableErrorTest() {
        System.out.println("Ошибочное означивание");
        boolean error = false;
        try {
            expression.eval("v=12");
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail();
        }
    }

    @Test
    void numberPrintTest() {
        System.out.println("Печать числа");
        Number num = new Number(12);
        num.print();
    }
}
