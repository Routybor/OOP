package ru.nsu.yakhimovich;

/**
 * Класс проверяющий корректность сборки.
 */
public class Main {
    /**
     * Конструктор по умолчанию.
     */
    public Main() {
        // Пустой конструктор для избежания warning
    }

    /**
     * Проверка сборки.
     *
     * @param args аргументы строки
     */
    public static void main(String[] args) {
        Expression expression = new Mul(new Number(2), new Number(2));
        expression.print();
    }
}
