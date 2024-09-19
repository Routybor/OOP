package ru.nsu.yakhimovich;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Юнит тесты для проверки методов класса.
 */
public class ExpressionUnitTest {
    String expressionStr = "3+2*x";
    Expression expression = ExpressionParser.parse(expressionStr);

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
        System.out.println("Упрощение выражения");
        expressionStr = "(x-0)*1-1*x - x*0-0*x + (3-3) + 3/3-1/1 + (x/1-0/x)/x";
        expression = ExpressionParser.parse(expressionStr);
        Expression simple = expression.simplify();
        simple.print();
        Assertions.assertEquals(simple.toString(), "0");
    }

    @Test
    void evalTest() {
        System.out.println("Вычисление выражения при x = 10 y = 13");
        double result = expression.eval("x=10; y=13");
        Assertions.assertEquals(result, 23);
    }

    @Test
    void derivativeTwoVarsTest() {
        System.out.println("Дифференцирование дроби");
        expressionStr = "(x+2)/(x-3)";
        expression = ExpressionParser.parse(expressionStr);
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
