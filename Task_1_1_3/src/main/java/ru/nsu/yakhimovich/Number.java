package ru.nsu.yakhimovich;

/**
 * Класс реализующий числа.
 */
class Number extends Expression {
    private final int value;

    /**
     * Конструктор создает число с указанным значением.
     * @param value значение числа
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Производная числа.
     * @param variable переменная относительно которой берется производная
     * @return производная
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0); // Производная от числа всегда 0
    }

    /**
     * Означивание числа.
     * @param variables переменная
     * @return значение числа
     */
    @Override
    public int eval(String variables) {
        return value;
    }

    /**
     * Стоковое значение числа.
     * @return строковое значение числа.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Вывод числа в консоль.
     */
    @Override
    public void print() {
        System.out.println(value);
    }
}

