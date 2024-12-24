package ru.nsu.yakhimovich.gradesbook;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс семестра.
 */
public class Term {
    private final Map<Types, Grade> termRecord;


    Term(int taskGrades, int testGrades, int colloqGrades,
         int examGrades, int difCreditGrades,
         int creditGrades, int practiceGrades,
         int vkrGrades) {
        termRecord = new HashMap<>();
        termRecord.put(Types.TASK, new Grade(taskGrades));
        termRecord.put(Types.TEST, new Grade(testGrades));
        termRecord.put(Types.COLLOQ, new Grade(colloqGrades));
        termRecord.put(Types.EXAM, new Grade(examGrades));
        termRecord.put(Types.DIF_CREDIT, new Grade(difCreditGrades));
        termRecord.put(Types.CREDIT, new Grade(creditGrades));
        termRecord.put(Types.PRACTICE, new Grade(practiceGrades));
        termRecord.put(Types.VKR, new Grade(vkrGrades));
    }

    /**
     * Объект в строку.
     *
     * @return строка
     */
    public String toText() {
        StringBuilder sb = new StringBuilder();
        for (Types controlType : Types.values()) {
            Grade grades = termRecord.get(controlType);
            sb.append(controlType.name()).append(":").append(grades.toText()).append(";");
        }
        return sb.toString();
    }

    /**
     * Строка в объект.
     *
     * @param text строка
     * @return объект
     */
    public static Term fromText(String text) {
        Map<Types, Grade> termRecord = new HashMap<>();
        String[] parts = text.split(";");
        for (String part : parts) {
            String[] mainParts = part.split("\\|");
            String[] controlParts = mainParts[0].split(":");
            int maxGrades = Integer.parseInt(controlParts[1]);
            Types controlType = Types.valueOf(controlParts[0]);
            Grade grades = new Grade(maxGrades);
            if (mainParts.length > 1) {
                String[] marks = mainParts[1].split("\t");
                for (String mark : marks) {
                    String[] markPair = mark.split(",");
                    grades.addGrade(Integer.parseInt(markPair[1]), markPair[0]);
                }
            }
            termRecord.put(controlType, grades);
        }
        Term term = new Term(0, 0, 0, 0, 0, 0, 0, 0);
        term.termRecord.clear();
        term.termRecord.putAll(termRecord);
        return term;
    }

    /**
     * Средний балл.
     *
     * @return значение
     */
    public double getTermAverageScore() {

        return termRecord.values()
                .stream()
                .mapToDouble(Grade::getAverage)
                .filter(x -> x > 0)
                .average()
                .orElse(0);
    }

    /**
     * Проверка на наличие оценок диф зачёта и экзамена.
     */
    public boolean hasMarks(int examMark, int difCreditMark) {
        Optional<Integer> res = termRecord.values()
                .stream()
                .flatMap(x -> x.getValues().stream())
                .filter(x -> x <= examMark)
                .findAny();

        Optional<Integer> res1 = termRecord.get(Types.EXAM)
                .getValues()
                .stream()
                .filter(x -> x <= difCreditMark)
                .findAny();
        return res.isPresent() || res1.isPresent();
    }

    /**
     * Просто получение записи для работы со стримами.
     */
    public Map<Types, Grade> getTermRecord() {
        return termRecord;
    }

    /**
     * Просто для одного случая получение оценки ВКР.
     */
    public int getVkrMark() {
        return termRecord.get(Types.VKR)
                .getValues()
                .stream()
                .findFirst()
                .orElse(0);
    }

    /**
     * Добавление оценки.
     */
    public void addGradeTerm(String subject, int grade, Types controlType) {
        termRecord.get(controlType).addGrade(grade, subject);
    }
}
