package ru.nsu.yakhimovich.expression;

/**
 * Класс, реализующий вычитание.
 */
class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор создаёт разность из двух частей: left, right.
     *
     * @param left  левая часть
     * @param right правая часть
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Производная разности.
     *
     * @param variable имя переменной по которой берется производная
     * @return производная
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Означивание разности.
     *
     * @param variables переменные и их значения.
     * @return результат означивания
     */
    @Override
    public double eval(String variables) {
        return left.eval(variables) - right.eval(variables);
    }

    /**
     * Стоковое значение разности.
     *
     * @return строка - разность
     */
    @Override
    public String toString() {
        String result = "(";
        result += left + "-" + right;
        return result + ")";
    }

    /**
     * Упрощение разности:
     * 1) разность двух чисел.
     * 2) вычитаемое = 0.
     * 3) уменьшаемое == вычитаемое.
     *
     * @return упрощенное значение
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // Разность констант
        if (simpleLeft instanceof Number simpleLeftNumber &&
                simpleRight instanceof Number simpleRightNumber) {
            return new Number(simpleLeftNumber.getValue() - simpleRightNumber.getValue());
        }

        // Вычитание одинаковых выражений
        if (simpleLeft.equals(simpleRight)) {
            return new Number(0);
        }

        // Вычитание 0
        if (simpleRight instanceof Number simpleRightNumber && simpleRightNumber.getValue() == 0) {
            return simpleLeft;
        }

        return new Sub(simpleLeft, simpleRight);
    }
}
