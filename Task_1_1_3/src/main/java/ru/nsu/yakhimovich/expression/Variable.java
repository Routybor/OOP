package ru.nsu.yakhimovich.expression;

/**
 * Класс реализующий переменные.
 */
class Variable extends ru.nsu.yakhimovich.expression.Expression {
    private final String name;

    /**
     * Конструктор создает переменную с указанным именем.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Производная переменной.
     *
     * @param variable имя переменной по которой берется производная
     * @return производная переменной 0/1
     */
    @Override
    public ru.nsu.yakhimovich.expression.Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1); // Производная переменной по себе = 1
        } else {
            return new Number(0); // Производная по другой переменной = 0
        }
    }

    /**
     * Означивание переменных и подставка значений в выражение.
     *
     * @param variables переменные и их значения
     * @return результат выражения с подставленными значениями
     */
    @Override
    public double eval(String variables) {
        String[] varAssignments = variables.split(";");
        for (String assignment : varAssignments) {
            String[] parts = assignment.trim().split("=");
            if (parts[0].trim().equals(name)) {
                return Integer.parseInt(parts[1].trim());
            }
        }
        throw new RuntimeException("Переменная " + name + " не определена!");
    }

    /**
     * Стоковое значение имени переменной.
     *
     * @return имя переменной
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Упрощение переменной, на деле - возвращение самой переменной.
     *
     * @return переменная
     */
    @Override
    public ru.nsu.yakhimovich.expression.Expression simplify() {
        return this; // Переменная не упрощается
    }

    /**
     * Сравнивание переменных.
     * Оригинальное работает не так как необходимо.
     *
     * @param obj сравниваемый объект
     * @return True/False
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable variable = (Variable) obj;
        return name.equals(variable.name); // Сравнивание переменных по именам
    }

    /**
     * Хэш код переменной, берется от имени, ведь у переменных уникальны имена.
     * @return хэш-код
     */
    @Override
    public int hashCode() {
        return name.hashCode(); // Хэш-код основывается на значении поля name
    }
}
