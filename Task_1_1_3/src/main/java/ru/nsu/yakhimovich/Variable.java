package ru.nsu.yakhimovich;

/**
 * Класс реализующий переменные.
 */
class Variable extends Expression {
    private final String name;

    /**
     * Конструктор создает переменную с указанным именем.
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Производная переменной.
     * @param variable имя переменной по которой берется производная
     * @return производная переменной 0/1
     */
    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1); // Производная переменной по себе = 1
        } else {
            return new Number(0); // Производная по другой переменной = 0
        }
    }

    /**
     * Означивание переменных и подставка значений в выражение.
     * @param variables переменные и их значения
     * @return результат выражения с подставленными значениями
     */
    @Override
    public int eval(String variables) {
        String[] varAssignments = variables.split(";");
        for (String assignment : varAssignments) {
            String[] parts = assignment.trim().split("=");
            if (parts[0].trim().equals(name)) {
                return Integer.parseInt(parts[1].trim());
            }
        }
        throw new RuntimeException("Variable " + name + " not defined.");
    }

    /**
     * Стоковое значение имени переменной.
     * @return имя переменной
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Вывод имени переменной в консоль.
     */
    @Override
    public void print() {
        System.out.println(name);
    }
}

