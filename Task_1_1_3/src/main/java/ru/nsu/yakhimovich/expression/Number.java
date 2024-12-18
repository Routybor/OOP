package ru.nsu.yakhimovich.expression;

/**
 * Класс, реализующий числа.
 */
class Number extends Expression {
    private final double value;

    /**
     * Конструктор создает число с указанным значением.
     *
     * @param value значение числа
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Производная числа.
     *
     * @param variable переменная, относительно которой берется производная
     * @return производная
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0); // Производная от числа = 0
    }

    /**
     * Означивание числа.
     *
     * @param variables переменная
     * @return значение числа
     */
    @Override
    public double eval(String variables) {
        return value;
    }

    /**
     * Стоковое значение числа.
     * Если целое - в значение попадает только целая часть.
     *
     * @return строковое значение числа
     */
    @Override
    public String toString() {
        String result;

        if ((int) value == value) {
            result = String.valueOf((int) value);
        } else {
            result = String.valueOf(value);
        }
        result = value < 0 ? "(" + result + ")" : result;

        return result;
    }

    /**
     * Упрощение числа, на деле - возвращение самого числа.
     *
     * @return число
     */
    @Override
    public Expression simplify() {
        return this; // Число не упрощается
    }

    /**
     * Получение значения числа.
     *
     * @return значение числа
     */
    public double getValue() {
        return this.value;
    }
}
