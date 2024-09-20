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
        Expression result = parseExpression();
        if (ind < input.length()) {
            throw new RuntimeException("Некорректный ввод= ошибочный символ" + input.charAt(ind));
        }
        return result;
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
            if (ind >= input.length() || input.charAt(ind) == '+' || input.charAt(ind) == '-'
                    || input.charAt(ind) == '*' || input.charAt(ind) == '/') {
                throw new RuntimeException("Некорректный ввод = повторные/завершающие операторы");
            }
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
            if (ind >= input.length() || input.charAt(ind) == '+' || input.charAt(ind) == '-'
                    || input.charAt(ind) == '*' || input.charAt(ind) == '/') {
                throw new RuntimeException("Некорректный ввод = повторные/завершающие операторы");
            }
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
        boolean isNegative = false;
        double scanned;

        if (input.charAt(ind) == '-') {
            isNegative = true;
            ind++;
        }
        if (ind >= input.length()) {
            throw new RuntimeException("Некорректный ввод = выражение не окончено");
        }
        if (input.charAt(ind) == '(') { // Выражения в скобках
            ind++;
            result = parseExpression();
            if (ind >= input.length() || input.charAt(ind) != ')') {
                throw new RuntimeException("Некорректный ввод = '(' не закрыта");
            }
            ind++;
        } else if (input.charAt(ind) == ')') {
            throw new RuntimeException("Некорректный ввод = ')' не была открыта" + ind);
        } else if (Character.isDigit(input.charAt(ind)) || input.charAt(ind) == '.') {
            boolean hasDot = false;
            while (ind < input.length() && (Character.isDigit(input.charAt(ind))
                    || (!hasDot && input.charAt(ind) == '.'))) {
                if (input.charAt(ind) == '.') {
                    hasDot = true;
                }
                sb.append(input.charAt(ind++));
            }
            scanned = Double.parseDouble(sb.toString());
            scanned = isNegative ? scanned * -1 : scanned;
            result = new Number(scanned);
        } else { // Переменные
            while (ind < input.length() && Character.isLetter(input.charAt(ind))) {
                sb.append(input.charAt(ind++));
            }
            if (sb.isEmpty()) {
                throw new RuntimeException("Некорректный ввод = отсутствует переменная/число");
            }
            result = new Variable(sb.toString());
        }

        return result;
    }
}
