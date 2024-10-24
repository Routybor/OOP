package ru.nsu.yakhimovich.expression;

/**
 * Класс, реализующий парсинг строк в объекты Expression.
 */
class ExpressionParser {
    private int ind = 0;
    private String input;

    /**
     * Основная функция, очищающая входную строку от пробелов и начинающая процесс парсинга.
     *
     * @param str строка, которую нужно обработать в объект Expression
     * @return объект Expression, представляющий данное выражение
     */
    public Expression parse(String str) {
        if (str.isEmpty()) {
            throw new IllegalStateException("Некорректный ввод= пустая строка");
        }
        input = str.replaceAll(" ", ""); // Очистка пробелов
        ind = 0;
        Expression result = parseExpression();
        if (ind < input.length()) {
            throw new IllegalStateException("Некорректный ввод=ошибочный символ");
        }
        return result;
    }

    /**
     * Проверка символа на его принадлежность списку валидных операторов.
     *
     * @param ch символ
     * @return True/False является ли валидным оператором
     */
    private boolean isOperator(char ch) {
        return "+-*/".indexOf(ch) != -1;
    }

    /**
     * Парсинг выражения, обработка сложения и вычитания.
     * Рекурсивный вызов parseTerm для обработки более приоритетных операций.
     *
     * @return объект Expression, представляющий результат парсинга
     */
    private Expression parseExpression() {
        Expression result = parseTerm(); // Обработка сначала умножения/деления
        Expression right;

        while (ind < input.length() && isOperator(input.charAt(ind))) {
            final var currentChar = input.charAt(ind++);
            if (ind >= input.length() || isOperator(input.charAt(ind))) {
                throw new IllegalStateException("Некорректный ввод=повтор/завершение оператором!");
            }
            right = parseTerm();
            switch (currentChar) {
                case '+':
                    result = new Add(result, right);
                    break;
                case '-':
                    result = new Sub(result, right);
                    break;
                default:
                    break;
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
    private Expression parseTerm() {
        Expression result = parseAtom(); // Обработка сначала чисел/переменных
        Expression right;

        while (ind < input.length() && ("*/".indexOf(input.charAt(ind)) != -1)) {
            final var currentChar = input.charAt(ind++);
            if (ind >= input.length() || isOperator(input.charAt(ind))) {
                throw new IllegalStateException("Некорректный ввод=повтор/завершение оператором!");
            }
            right = parseAtom();
            switch (currentChar) {
                case '*':
                    result = new Mul(result, right);
                    break;
                case '/':
                    result = new Div(result, right);
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    /**
     * Парсинг атомарных элементов выражения (числа/переменные/выражения в скобках).
     *
     * @return объект Expression, представляющий атомарное выражение
     */
    private Expression parseAtom() {
        Expression result;
        StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        double scanned;

        if (input.charAt(ind) == '-') {
            isNegative = true;
            ind++;
        }
        if (ind >= input.length()) {
            throw new IllegalStateException("Некорректный ввод = выражение не окончено!");
        }
        if (input.charAt(ind) == '(') { // Выражения в скобках
            ind++;
            result = parseExpression();
            if (ind >= input.length() || input.charAt(ind) != ')') {
                throw new IllegalStateException("Некорректный ввод = '(' не закрыта!");
            }
            ind++;
        } else if (input.charAt(ind) == ')') {
            throw new IllegalStateException("Некорректный ввод = ')' не была открыта!");
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
                throw new IllegalStateException("Некорректный ввод=отсутствует переменная/число!");
            }
            result = new Variable(sb.toString());
        }

        return result;
    }
}
