package ru.nsu.yakhimovich.expression;

/**
 * Класс реализующий сумму.
 */
class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор создаёт сумму из двух частей: left, right.
     *
     * @param left  левая часть
     * @param right правая часть
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Производная суммы.
     *
     * @param variable имя переменной по которой берется производная
     * @return производная
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Означивание суммы.
     *
     * @param variables переменные и их значения.
     * @return результат означивания
     */
    @Override
    public double eval(String variables) {
        return left.eval(variables) + right.eval(variables);
    }

    /**
     * Стоковое значение суммы.
     *
     * @return строка - сумма
     */
    @Override
    public String toString() {
        String result = "(";
        result += left + "+" + right;
        return result + ")";
    }

    /**
     * Упрощение суммы:
     * 1) сумма двух чисел.
     * 2) одно из слагаемых = 0.
     *
     * @return упрощенное значение
     */
    @Override
    public Expression simplify() {
        Expression simpleLeft = left.simplify();
        Expression simpleRight = right.simplify();

        // Сложение констант
        if (simpleLeft instanceof Number simpleLeftNumber &&
                simpleRight instanceof Number simpleRightNumber) {
            return new Number(simpleLeftNumber.getValue() + simpleRightNumber.getValue());
        }

        /*
           Добавление 0.
           Используется означивание по пустой строке, чтобы получить значение чисел.
           Перед получением значения необходимо удостовериться, что это число.
           Если этого не сделать - программа упадет, ведь попытается означить несущ переменную.
        */
        if (simpleLeft instanceof Number simpleLeftNumber && simpleLeftNumber.getValue() == 0) {
            return simpleRight; // 0 + x => x
        }

        if (simpleRight instanceof Number simpleRightNumber && simpleRightNumber.getValue() == 0) {
            return simpleLeft; // x + 0 => x
        }
        return new Add(simpleLeft, simpleRight);
    }
}
