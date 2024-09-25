package ru.nsu.yakhimovich.expression;

import java.util.Scanner;

/**
 * Сканирование выражение из терминала и вывод получившегося объекта.
 */
public class Main {
    /**
     * Класс.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение = ");
        String inputExpression = scanner.nextLine();
        Expression parsedExpression = ExpressionParser.parse(inputExpression);
        System.out.printf("%nСоздано выражение = %s%n ", parsedExpression);
    }
}
