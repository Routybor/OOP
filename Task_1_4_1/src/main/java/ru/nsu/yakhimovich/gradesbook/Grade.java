package ru.nsu.yakhimovich.gradesbook;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, описывающий оценки.
 */
public class Grade {
    private final Map<String, Integer> grades;
    private final int maxGrades;

    public Grade(int maxGrades) {
        this.grades = new HashMap<>();
        this.maxGrades = maxGrades;
    }

    /**
     * Получение среднего арифметического.
     * @return значение
     */
    public double getAverage() {
        return grades.values()
                .stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0);
    }

    /**
     * Получение значений оценок.
     * @return оценки
     */
    public Collection<Integer> getValues() {
        return grades.values();
    }

    /**
     * Добавление оценки.
     * @param grade значение
     * @param subject предмет
     */
    public void addGrade(int grade, String subject) {
        if (!grades.containsKey(subject) && grades.size() < maxGrades) {
            grades.put(subject, grade);
        }

    }

    /**
     * Объект в строку.
     * @return строка
     */
    public String toText() {
        StringBuilder sb = new StringBuilder();
        sb.append(maxGrades).append("|");
        int count = 0;
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            sb.append(entry.getKey()).append(",").append(entry.getValue());
            if (++count < grades.size()) {
                sb.append("\t");
            }
        }
        return sb.toString();
    }

    /**
     * Строка в объект.
     * @param text строка
     * @return объект
     */
    public static Grade fromText(String text) {
        String[] parts = text.split(",");
        int grade = Integer.parseInt(parts[1]);
        return new Grade(grade);
    }

}