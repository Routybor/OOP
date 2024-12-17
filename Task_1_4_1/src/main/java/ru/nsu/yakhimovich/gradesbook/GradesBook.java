package ru.nsu.yakhimovich.gradesbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Зачётная книжка.
 */
public class GradesBook {
    private final List<Term> termList;

    public GradesBook(List<Term> termList) {
        this.termList = termList;
    }

    /**
     * Возможность бюджета.
     * @param currentTerm семестр
     * @return True/False
     */
    public boolean canBudgetTransfer(int currentTerm) {
        if (currentTerm < 2) {
            return false;
        }
        return !termList.get(currentTerm - 2).hasMarks(3, 2)
                && !termList.get(currentTerm - 1).hasMarks(3, 2);
    }

    /**
     * Возможность красного диплома.
     * @param currentTerm семестр
     * @return True/False
     */
    public boolean canGetRedDiploma(int currentTerm) {
        long totalMarks = termList.stream()
                .flatMap(term -> term.getTermRecord()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey() != Types.CREDIT)
                        .flatMap(entry -> entry.getValue().getValues().stream()))
                .count();

        long markFive = termList.stream()
                .flatMap(term -> term.getTermRecord()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey() != Types.CREDIT)
                        .flatMap(entry -> entry.getValue().getValues().stream()))
                .filter(mark -> mark == 5)
                .count();
        if ((double) markFive / totalMarks < 0.75) {
            return false;
        }

        long markThree = termList.stream()
                .flatMap(term -> term.getTermRecord()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey() != Types.CREDIT)
                        .flatMap(entry -> entry.getValue().getValues().stream()))
                .filter(mark -> mark == 3)
                .count();
        if (markThree > 0) {
            return false;
        }
        if (currentTerm != 8) {
            return (double) markFive / totalMarks >= 0.75;
        }
        return termList.get(currentTerm - 1).getVkrMark() == 5;
    }

    /**
     * Добавление оценки.
     * @param term семестр
     * @param subject предмет
     * @param grade оценка
     * @param type тип
     */
    public void addGrade(int term, String subject,
                         int grade, Types type) {
        termList.get(term - 1).addGradeTerm(subject, grade, type);
    }

    /**
     * Средний балл.
     * @return значение.
     */
    public double getAverageScore() {
        return termList.stream()
                .mapToDouble(Term::getTermAverageScore)
                .filter(x -> x > 0)
                .average()
                .orElse(0);
    }

    /**
     * Большая стипендия.
     * @param currentTerm семестр
     * @return True/False
     */
    public boolean canGetIncreasedScholarship(int currentTerm) {
        return termList.get(currentTerm - 1)
                .getTermRecord()
                .values()
                .stream()
                .mapToLong(grade -> grade.getValues().size())
                .sum()
                ==
                termList.get(currentTerm - 1)
                        .getTermRecord()
                        .entrySet()
                        .stream()
                        .flatMap(entry -> entry.getValue().getValues().stream())
                        .filter(x -> x == 5)
                        .count();
    }

    /**
     * Сохранение в файл.
     * @param filePath путь
     * @throws IOException ошибка
     */
    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Term term : termList) {
                writer.write(term.toText());
                writer.newLine();
            }
        }
    }

    /**
     * Из файла.
     * @param filePath путь
     * @return зачетка
     * @throws IOException ошибка
     */
    public static GradesBook loadFromFile(String filePath) throws IOException {
        List<Term> termList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                termList.add(Term.fromText(line));
            }
        }
        return new GradesBook(termList);
    }
}