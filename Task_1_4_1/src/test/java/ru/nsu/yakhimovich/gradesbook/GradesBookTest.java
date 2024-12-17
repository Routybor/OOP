package ru.nsu.yakhimovich.gradesbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тесты.
 */
public class GradesBookTest {
    GradesBook gradesBook;

    @BeforeEach
    @DisplayName("SetUp")
    void setUp() {
        ArrayList<Term> termList = new ArrayList<>();
        termList.add(new Term(2, 3, 1, 3, 3, 3, 0, 0));
        termList.add(new Term(2, 3, 1, 3, 3, 2, 0, 0));
        termList.add(new Term(3, 2, 0, 2, 6, 0, 0, 0));
        termList.add(new Term(2, 1, 0, 5, 5, 0, 0, 0));
        termList.add(new Term(2, 2, 0, 3, 4, 0, 0, 0));
        termList.add(new Term(2, 2, 0, 2, 6, 0, 0, 0));
        termList.add(new Term(2, 0, 0, 1, 4, 1, 1, 0));
        termList.add(new Term(0, 0, 0, 0, 0, 0, 0, 1));
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
    @DisplayName("CanBudgetTransferTest")
    void canBudgetTransferTest() {
        assertTrue(gradesBook.canBudgetTransfer(2));
    }

    @Test
    @DisplayName("CantBudgetTransferTest")
    void cantBudgetTransferTest() {
        gradesBook.addGrade(2, "English", 3, Types.DIF_CREDIT);
        assertFalse(gradesBook.canBudgetTransfer(2));
    }

    @Test
    @DisplayName("CanGetRedDiplomaTest")
    void canGetRedDiplomaTest() {
        assertTrue(gradesBook.canGetRedDiploma(2));
    }

    @Test
    @DisplayName("CantGetRedDiplomaTest")
    void cantGetRedDiplomaTest() {
        gradesBook.addGrade(3, "PE", 3, Types.EXAM);
        assertFalse(gradesBook.canGetRedDiploma(3));
    }

    @Test
    @DisplayName("AverageScoreTest")
    void averageScoreTest() {
        gradesBook.addGrade(2, "Wish mat", 3, Types.DIF_CREDIT);
        assertEquals((double) 58 / 12, gradesBook.getAverageScore());
    }

    @Test
    @DisplayName("ScholarshipTest")
    void scholarshipTest() {
        assertTrue(gradesBook.canGetIncreasedScholarship(2));

        gradesBook.addGrade(2, "Wish mat", 4, Types.DIF_CREDIT);
        assertFalse(gradesBook.canGetIncreasedScholarship(2));
    }

    @Test
    @DisplayName("CantCheckFilesTest")
    void filesTest() throws IOException {
        String filePath = "gradesBook.txt";
        gradesBook.addGrade(2, "Wish mat", 3, Types.DIF_CREDIT);
        gradesBook.saveToFile(filePath);

        GradesBook loadedBook = GradesBook.loadFromFile(filePath);
        assertEquals((double) 58 / 12, loadedBook.getAverageScore());
    }
}