package ru.nsu.yakhimovich;

/**
 * Класс, реализующий логику выражений.
 */
abstract class Expression {
    public abstract void print(); // Печать выражения

    public abstract Expression derivative(String variable); // Дифференцирование

    public abstract double eval(String variables); // Вычисление выражения при означивании

    public abstract Expression simplify(); // Упрощение выражения
}
