package ru.nsu.yakhimovich;

/**
 * Класс реализующий произведение.
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
    public int eval(String variables) {
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
}
