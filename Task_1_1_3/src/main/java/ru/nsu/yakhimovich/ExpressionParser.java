package ru.nsu.yakhimovich;

/**
 * Класс, реализующий парсинг строк в объекты Expression.
 */
class ExpressionParser {
    private static int ind = 0;
    private static String input;

    /**
     * Основная функция, очищающая входную строку от пробелов и начинающая процесс парсинга.
     *
     * @param str строка, которую нужно обработать в объект Expression
     * @return объект Expression, представляющий данное выражение
     */
    public static Expression parse(String str) {
        input = str.replaceAll(" ", ""); // Очистка пробелов
        ind = 0;
        return parseExpression();
    }

    /**
     * Парсинг выражения, обработка сложения и вычитания.
     * Рекурсивный вызов parseTerm для обработки более приоритетных операций.
     *
     * @return объект Expression, представляющий результат парсинга
     */
    private static Expression parseExpression() {
        Expression result = parseTerm(); // Обработка сначала умножения/деления
        Expression right;
        char operation;

        while (ind < input.length() && (input.charAt(ind) == '+' || input.charAt(ind) == '-')) {
            operation = input.charAt(ind++);
            right = parseTerm();
            if (operation == '+') {
                result = new Add(result, right);
            } else {
                result = new Sub(result, right);
            }
        }

        return result;
    }

    /**
     * Парсинг термов, обработка умножения и деления.
     * Рекурсивный вызов parseAtom для работы с простыми выражениями.
     *
     * @return объект Expression, представляющий результат парсинга терма
     */
    private static Expression parseTerm() {
        Expression result = parseAtom(); // Обработка сначала чисел/переменных
        Expression right;
        char operation;

        while (ind < input.length() && (input.charAt(ind) == '*' || input.charAt(ind) == '/')) {
            operation = input.charAt(ind++);
            right = parseAtom();
            if (operation == '*') {
                result = new Mul(result, right);
            } else {
                result = new Div(result, right);
            }
        }

        return result;
    }

    /**
     * Парсинг атомарных элементов выражения (числа/переменные/выражения в скобках).
     *
     * @return объект Expression, представляющий атомарное выражение
     */
    private static Expression parseAtom() {
        Expression result;
        StringBuilder sb = new StringBuilder();

        if (input.charAt(ind) == '(') { // Выражения в скобках
            ind++;
            result = parseExpression();
            ind++; // Отбрасывание ')'
        } else if (Character.isDigit(input.charAt(ind)) || input.charAt(ind) == '.') { // Числа с учетом десятичных точек
            boolean hasDot = false;
            while (ind < input.length() && (Character.isDigit(input.charAt(ind)) || (!hasDot && input.charAt(ind) == '.'))) {
                if (input.charAt(ind) == '.') {
                    hasDot = true;
                }
                sb.append(input.charAt(ind++));
            }
            result = new Number(Double.parseDouble(sb.toString()));
        } else { // Переменные
            while (ind < input.length() && Character.isLetter(input.charAt(ind))) {
                sb.append(input.charAt(ind++));
            }
            result = new Variable(sb.toString());
        }

        return result;
    }
}
