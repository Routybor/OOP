package ru.nsu.yakhimovich;

/**
 * Класс реализующий логику выражений.
 */
abstract class Expression {
    public abstract void print(); // Печать выражения

    public abstract Expression derivative(String variable); // Дифференцирование

    public abstract int eval(String variables); // Вычисление выражения при означивании переменных
}
