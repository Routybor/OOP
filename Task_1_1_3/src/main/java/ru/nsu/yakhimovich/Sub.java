package ru.nsu.yakhimovich;

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
    public int eval(String variables) {
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
     * Вывод разности в консоль.
     */
    @Override
    public void print() {
        System.out.println(this);
    }
}
