package ru.nsu.yakhimovich;

/**
 * Класс, проверяющий корректность сборки.
 */
public class Main {
    /**
     * Проверка корректной сборки и работы программы.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String expressionStr = "3 +( 2 * x + 2) * y";
        Expression e = ExpressionParser.parse(expressionStr);

        e.print(); // (3+(((2*x)+2)*y))
        System.out.println();
    }
}

