package ru.nsu.yakhimovich.gradesbook;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тесты для проверки функциональности класса GradesBook.
 */
public class GradesBookTest {

    private GradesBook gradesBook;

    @BeforeEach
    void setUp() {
        List<Term> termList = List.of(
                new Term(2, 3, 1, 3, 3, 3, 0, 0),
                new Term(2, 3, 1, 3, 3, 2, 0, 0),
                new Term(3, 2, 0, 2, 6, 0, 0, 0),
                new Term(2, 1, 0, 5, 5, 0, 0, 0),
                new Term(2, 2, 0, 3, 4, 0, 0, 0),
                new Term(2, 2, 0, 2, 6, 0, 0, 0),
                new Term(2, 0, 0, 1, 4, 1, 1, 0),
                new Term(0, 0, 0, 0, 0, 0, 0, 1)
        );

        gradesBook = new GradesBook(termList);
        gradesBook.addGrade(1, "C", 5, Types.EXAM);
        gradesBook.addGrade(1, "OOP", 5, Types.EXAM);
        gradesBook.addGrade(1, "PE", 5, Types.EXAM);
        gradesBook.addGrade(1, "C", 5, Types.DIF_CREDIT);
        gradesBook.addGrade(1, "OOP", 5, Types.DIF_CREDIT);
        gradesBook.addGrade(1, "PE", 5, Types.DIF_CREDIT);
        gradesBook.addGrade(2, "C", 5, Types.EXAM);
        gradesBook.addGrade(2, "OOP", 5, Types.EXAM);
        gradesBook.addGrade(2, "PE", 5, Types.EXAM);
        gradesBook.addGrade(2, "C", 5, Types.DIF_CREDIT);
        gradesBook.addGrade(2, "OOP", 5, Types.DIF_CREDIT);
    }

    @Test
    void canBudgetTransferTest() {
        assertTrue(gradesBook.canBudgetTransfer(2),
                "Студент должен быть допущен к переводу на бюджет");
    }

    @Test
    void cantBudgetTransferTest() {
        gradesBook.addGrade(2, "English", 3, Types.DIF_CREDIT);
        assertFalse(gradesBook.canBudgetTransfer(2),
                "Студент не должен быть допущен к переводу на бюджет при низких оценках");
    }

    @Test
    void canGetRedDiplomaTest() {
        assertTrue(gradesBook.canGetRedDiploma(2),
                "Студент должен быть допущен к получению красного диплома");
    }

    @Test
    void cantGetRedDiplomaTest() {
        gradesBook.addGrade(3, "PE", 3, Types.EXAM);
        assertFalse(gradesBook.canGetRedDiploma(3),
                "Студент не должен быть допущен к получению красного диплома при низких оценках");
    }

    @Test
    void averageScoreTest() {
        gradesBook.addGrade(2, "ORG", 3, Types.DIF_CREDIT);
        double expectedAverage = 58.0 / 12;
        assertEquals(expectedAverage, gradesBook.getAverageScore(), 0.01,
                "Расчёт среднего балла выполнен некорректно");
    }

    @Test
    void scholarshipTest() {
        assertTrue(gradesBook.canGetIncreasedScholarship(2),
                "Студент должен быть допущен к получению повышенной стипендии");

        gradesBook.addGrade(2, "ORG", 4, Types.DIF_CREDIT);
        assertFalse(gradesBook.canGetIncreasedScholarship(2),
                "Студент не должен быть допущен к получению повышенной стипендии после снижения оценок");
    }

    @Test
    void filesTest() throws IOException {
        String filePath = "gradesBook.txt";
        gradesBook.addGrade(2, "ORG", 3, Types.DIF_CREDIT);
        gradesBook.saveToFile(filePath);

        GradesBook loadedBook = GradesBook.loadFromFile(filePath);
        double expectedAverage = 58.0 / 12;
        assertEquals(expectedAverage, loadedBook.getAverageScore(), 0.01,
                "Функционал сохранения/загрузки файла выполнен некорректно");
    }
}