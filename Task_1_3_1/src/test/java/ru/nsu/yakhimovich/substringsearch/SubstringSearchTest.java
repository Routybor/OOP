package ru.nsu.yakhimovich.substringsearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты поиска подстроки.
 */
public class SubstringSearchTest {

    private String curTestFile;

    private void createTestFile(String fileName, String content) throws IOException {
        curTestFile = fileName; // Сохранение имя текущего файла для последующего удаления
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }

    @AfterEach
    public void deleteTestFile() {
        if (curTestFile != null) {
            File file = new File(curTestFile);
            if (file.exists()) {
                boolean delete = file.delete();
            }
            curTestFile = null;
        }
    }

    @Test
    public void testFindSubstringMultipleOccurrences() throws IOException {
        String fileName = "test1.txt";
        createTestFile(fileName, "абракадабра");

        List<Integer> result = SubstringSearch.find(fileName, "бра");
        assertEquals(List.of(1, 8), result, "Indices should match expected values [1, 8]");
    }

    @Test
    public void testFindSubstringSingleOccurrence() throws IOException {
        String fileName = "test2.txt";
        createTestFile(fileName, "hello world");

        List<Integer> result = SubstringSearch.find(fileName, "world");
        assertEquals(List.of(6), result, "Index should be [6]");
    }

    @Test
    public void testFindSubstringNoOccurrence() throws IOException {
        String fileName = "test3.txt";
        createTestFile(fileName, "abcdefg");

        List<Integer> result = SubstringSearch.find(fileName, "xyz");
        assertEquals(List.of(), result, "There should be no matches");
    }

    @Test
    public void testFindSubstringAtStart() throws IOException {
        String fileName = "test4.txt";
        createTestFile(fileName, "abra");

        List<Integer> result = SubstringSearch.find(fileName, "abra");
        assertEquals(List.of(0), result, "Index should be [0]");
    }

    @Test
    public void testFindLargeFile() throws IOException {
        String fileName = "large_test.txt";
        createTestFile(fileName, "абракадабра".repeat(1000000));

        List<Integer> result = SubstringSearch.find(fileName, "бра");
        assertEquals(2000000, result.size(), "Should find 200,000 occurrences");
    }
}
