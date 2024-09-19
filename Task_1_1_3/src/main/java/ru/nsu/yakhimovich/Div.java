package ru.nsu.yakhimovich;

/**
 * Класс, реализующий деление.
 */
class Div extends Expression {
    private final Expression left;
    private final Expression right;

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
    public Expression derivative(String variable) {
        Expression numerator = new Sub(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }

    /**
     * Означивание деления.
     *
     * @param variables переменные и их значения.
     * @return результат означивания
     */
    @Override
    public int eval(String variables) {
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
     * Вывод деления в консоль.
     */
    @Override
    public void print() {
        System.out.println(this);
    }
}
