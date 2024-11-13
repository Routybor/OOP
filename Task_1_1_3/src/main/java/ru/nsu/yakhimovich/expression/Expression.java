package ru.nsu.yakhimovich.expression;

/**
 * Класс, реализующий логику выражений.
 */
abstract class Expression {
    public abstract Expression derivative(String variable); // Дифференцирование

    public abstract double eval(String variables); // Вычисление выражения при означивании

    public abstract Expression simplify(); // Упрощение выражения

    public void print() { // Печать выражения
        System.out.println(this);
    }
}
