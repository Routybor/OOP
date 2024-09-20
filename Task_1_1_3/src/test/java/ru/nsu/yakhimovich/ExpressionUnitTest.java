package ru.nsu.yakhimovich;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Юнит тесты для проверки методов класса.
 */
public class ExpressionUnitTest {
    String expressionStr = "3+2*x";
    Expression expression = ExpressionParser.parse(expressionStr);
    Expression simple, derivative;
    boolean error;
    double result;

    @Test
    void printTest1() {
        System.out.println("Печать выражения");
        expression.print();
        Assertions.assertEquals(expression.toString(), "(3+(2*x))");
    }

    @Test
    void derivativeTest() {
        System.out.println("Дифференцирование по переменной x");
        derivative = expression.derivative("x");
        derivative.print();
        Assertions.assertEquals(derivative.toString(), "(0+((0*x)+(2*1)))");
    }

    @Test
    void printTest2() {
        System.out.println("Упрощение выражения");
        //                    0      -    0    +   0   +    0    +       1
        expressionStr = "(x-0)*1-1*x - x*0-0*x + (3-3) + 3/3-1/1 + (x/1-0/x)/x";
        expression = ExpressionParser.parse(expressionStr);
        simple = expression.simplify();
        simple.print();
        Assertions.assertEquals(simple.toString(), "1");
    }

    @Test
    void evalTest() {
        System.out.println("Вычисление выражения при x = 10 y = 13");
        result = expression.eval("x=10; y=13");
        Assertions.assertEquals(result, 23);
    }

    @Test
    void derivativeTwoVarsTest() {
        System.out.println("Дифференцирование дроби");
        expressionStr = "(x+2)/(x-3)";
        expression = ExpressionParser.parse(expressionStr);
        derivative = expression.derivative("x");
        simple = derivative.simplify();
        simple.print();
        Assertions.assertEquals(simple.toString(), "(((x-3)-(x+2))/((x-3)*(x-3)))");
    }

    @Test
    void variableErrorTest() {
        System.out.println("Ошибочное означивание");
        error = false;
        try {
            expression.eval("v=12");
        } catch (Exception e) {
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
        Assertions.assertEquals(num.toString(), "12");
    }

    @Test
    void negativeNumberTest() {
        System.out.println("Ввод отрицательного числа");
        expressionStr = "2+(-2)";
        expression = ExpressionParser.parse(expressionStr);
        simple = expression.simplify();
        result = simple.eval("");
        Assertions.assertEquals(result, 0);
    }

    @Test
    void incorrectInputDoublePlus() {
        System.out.println("Некорректный ввод = 2++2");
        expressionStr = "2++2";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '2++2'");
        }
    }

    @Test
    void incorrectInputDoubleMinus() {
        System.out.println("Некорректный ввод = 2--2");
        expressionStr = "2--2";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '2--2'");
        }
    }

    @Test
    void incorrectInputTrailingOperator() {
        System.out.println("Некорректный ввод = 22+");
        expressionStr = "22+";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '22+'");
        }
    }

    @Test
    void incorrectInputMissingOperand() {
        System.out.println("Некорректный ввод = +2");
        expressionStr = "+2";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '+2'");
        }
    }

    @Test
    void incorrectInputUnclosedParenthesis() {
        System.out.println("Некорректный ввод = (2+2");
        expressionStr = "(2+2";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '(2+2'");
        }
    }

    @Test
    void incorrectInputExtraClosingParenthesis() {
        System.out.println("Некорректный ввод = 2+2)");
        expressionStr = "2+2)";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '2+2)'");
        }
    }

    @Test
    void incorrectInputOperatorAfterOpeningParenthesis() {
        System.out.println("Некорректный ввод = (2+*3)");
        expressionStr = "(2+*3)";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '(2+*3)'");
        }
    }

    @Test
    void incorrectInputOperatorBeforeClosingParenthesis() {
        System.out.println("Некорректный ввод = (2+3*)");
        expressionStr = "(2+3*)";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '(2+3*)'");
        }
    }

    @Test
    void incorrectInputEmptyParentheses() {
        System.out.println("Некорректный ввод = ()");
        expressionStr = "()";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '()'");
        }
    }

    @Test
    void incorrectInputEmptyExpression() {
        System.out.println("Некорректный ввод = пустая строка");
        expressionStr = "";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при empty string");
        }
    }

    @Test
    void incorrectInputInvalidCharacters() {
        System.out.println("Некорректный ввод = 2+@2");
        expressionStr = "2+@2";
        error = false;
        try {
            expression = ExpressionParser.parse(expressionStr);
        } catch (RuntimeException e) {
            error = true;
        }
        if (!error) {
            Assertions.fail("Ожидалось RuntimeException при '2+@2'");
        }
    }
}
