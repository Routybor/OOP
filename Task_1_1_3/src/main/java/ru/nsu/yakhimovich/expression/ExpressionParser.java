package ru.nsu.yakhimovich.expression;

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
     * Проверка символа на его принадлежность списку валидных операторов.
     *
     * @param ch символ
     * @return True/False является ли валидным оператором
     */
    private static boolean isOperator(char ch) {
        return "+-*/".indexOf(ch) != -1;
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

        while (ind < input.length() && isOperator(input.charAt(ind))) {
            final var currentChar = input.charAt(ind++);
            if (ind >= input.length() || isOperator(input.charAt(ind))) {
                throw new RuntimeException("Некорректный ввод = повторные/завершающие операторы!");
            }
            right = parseTerm();
            result = switch (currentChar) {
                case '+' -> new Add(result, right);
                case '-' -> new Sub(result, right);
                default -> result;
            };
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

        while (ind < input.length() && ("*/".indexOf(input.charAt(ind)) != -1)) {
            final var currentChar = input.charAt(ind++);
            if (ind >= input.length() || isOperator(input.charAt(ind))) {
                throw new RuntimeException("Некорректный ввод = повторные/завершающие операторы!");
            }
            right = parseAtom();
            result = switch (currentChar) {
                case '*' -> new Mul(result, right);
                case '/' -> new Div(result, right);
                default -> result;
            };
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
            throw new RuntimeException("Некорректный ввод = выражение не окончено!");
        }
        if (input.charAt(ind) == '(') { // Выражения в скобках
            ind++;
            result = parseExpression();
            if (ind >= input.length() || input.charAt(ind) != ')') {
                throw new RuntimeException("Некорректный ввод = '(' не закрыта!");
            }
            ind++;
        } else if (input.charAt(ind) == ')') {
            throw new RuntimeException("Некорректный ввод = ')' не была открыта!");
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
            if (sb.length() == 0) {
                throw new RuntimeException("Некорректный ввод = отсутствует переменная/число!");
            }
            result = new Variable(sb.toString());
        }

        return result;
    }
}
