package ru.nsu.yakhimovich;

/**
 * Класс, реализующий произведение.
 */
class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор создаёт произведение из двух частей: left, right.
     *
     * @param left  левая часть
     * @param right правая часть
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Производная произведения.
     *
     * @param variable имя переменной по которой берется производная
     * @return производная
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
    }

    /**
     * Означивание произведения.
     *
     * @param variables переменные и их значения.
     * @return результат означивания
     */
    @Override
    public double eval(String variables) {
        return left.eval(variables) * right.eval(variables);
    }

    /**
     * Стоковое значение произведения.
     *
     * @return строка - произведение
     */
    @Override
    public String toString() {
        String result = "(";
        result += left + "*" + right;
        return result + ")";
    }

    /**
     * Вывод произведения в консоль.
     */
    @Override
    public void print() {
        System.out.println(this);
    }

    /**
     * Упрощение деления:
     * 1) умножение двух чисел.
     * 2) умножение на 1.
     * 3) умножение на 0.
     *
     * @return упрощенное значение
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // Умножение констант
        if (simpleLeft instanceof Number && simpleRight instanceof Number) {
            return new Number(simpleLeft.eval("") * simpleRight.eval(""));
        }

        // Умножение на 0
        if (simpleLeft instanceof Number && simpleLeft.eval("") == 0
         || simpleRight instanceof Number && simpleRight.eval("") == 0) {
            return new Number(0);
        }

        // Умножение на 1
        if (simpleLeft instanceof Number && simpleLeft.eval("") == 1) {
            return simpleRight;
        }
        if (simpleRight instanceof Number && simpleRight.eval("") == 1) {
            return simpleLeft;
        }

        return new Mul(simpleLeft, simpleRight);
    }
}
