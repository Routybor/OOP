package ru.nsu.yakhimovich;

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
    public int eval(String variables) {
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
     * Вывод суммы в консоль.
     */
    @Override
    public void print() {
        System.out.println(this);
    }
}
