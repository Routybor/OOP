package ru.nsu.yakhimovich.expression;

/**
 * Класс, реализующий деление.
 */
class Div extends ru.nsu.yakhimovich.expression.Expression {
    private final ru.nsu.yakhimovich.expression.Expression left;
    private final ru.nsu.yakhimovich.expression.Expression right;

    /**
     * Конструктор создаёт деление из двух частей: left, right.
     *
     * @param left  левая часть
     * @param right правая часть
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Производная деления.
     *
     * @param variable имя переменной по которой берется производная
     * @return производная
     */
    @Override
    public ru.nsu.yakhimovich.expression.Expression derivative(String variable) {
        Expression numerator = new Sub(new Mul(left.derivative(variable), right),
                               new Mul(left, right.derivative(variable)));
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }

    /**
     * Означивание деления.
     * Ошибка при делении на ноль.
     *
     * @param variables переменные и их значения.
     * @return результат означивания
     */
    @Override
    public double eval(String variables) {
        return left.eval(variables) / right.eval(variables);
    }

    /**
     * Стоковое значение деления.
     *
     * @return строка - деление
     */
    @Override
    public String toString() {
        String result = "(";
        result += left + "/" + right;
        return result + ")";
    }

    /**
     * Упрощение деления:
     * 1) деление двух чисел.
     * 2) деление на 1.
     * 3) деление 0.
     *
     * @return упрощенное значение
     */
    @Override
    public ru.nsu.yakhimovich.expression.Expression simplify() {
        ru.nsu.yakhimovich.expression.Expression simpleLeft = left.simplify();
        ru.nsu.yakhimovich.expression.Expression simpleRight = right.simplify();

        // Деление констант
        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            if (simpleRight.eval("") == 0) {
                throw new ArithmeticException("Деление на ноль!");
            }
            return new Number(simpleLeft.eval("") / simpleRight.eval(""));
        }

        // Деление одинаковых выражений
        if (simpleLeft.equals(simpleRight)) {
            return new Number(1);
        }

        // Деление 0
        if (simpleLeft instanceof Number && simpleLeft.eval("") == 0) {
            return new Number(0); // Деление 0
        }

        // Деление на 1
        if (simpleRight instanceof Number && simpleRight.eval("") == 1) {
            return simpleLeft; // Деление на 1
        }
        return new Div(simpleLeft, simpleRight);
    }
}
