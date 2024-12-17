package ru.nsu.yakhimovich.substringsearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты поиска подстроки.
 */
public class SubstringSearchTest {

    private final Set<String> testFiles = new HashSet<>();

    /**
     * Создание файла для теста.
     *
     * @param fileName имя файла
     * @param content  наполнение файла
     * @throws IOException ошибка создания/записи
     */
    private void createTestFile(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
        testFiles.add(fileName);
    }

    @AfterEach
    void cleanUpTestFiles() {
        for (String fileName : testFiles) {
            File file = new File(fileName);
            if (file.exists() && !file.delete()) {
                System.err.println("Failed to delete test file: " + fileName);
            }
        }
        testFiles.clear();
    }

    @Test
    public void testFindSubstringMultipleOccurrences() throws IOException {
        String fileName = "test1.txt";
        createTestFile(fileName, "абракадабра");

        List<Long> result = SubstringSearch.find(fileName, "бра");
        Assertions.assertEquals(List.of(1L, 8L), result, "Ожидалось. Индексы = [1, 8]");
    }

    @Test
    public void testFindSubstringSingleOccurrence() throws IOException {
        String fileName = "test2.txt";
        createTestFile(fileName, "hello worm?");

        List<Long> result = SubstringSearch.find(fileName, "worm");
        Assertions.assertEquals(List.of(6L), result, "Ожидалось. Индексы = [6]");
    }

    @Test
    public void testFindSubstringNoOccurrence() throws IOException {
        String fileName = "test3.txt";
        createTestFile(fileName, "abcdefg");

        List<Long> result = SubstringSearch.find(fileName, "xyz");
        Assertions.assertEquals(List.of(), result, "Ожидалось. Не найдено");
    }

    @Test
    public void testFindSubstringAtStart() throws IOException {
        String fileName = "test4.txt";
        createTestFile(fileName, "abra无知很酷");

        List<Long> result = SubstringSearch.find(fileName, "abra");
        Assertions.assertEquals(List.of(0L), result, "Ожидалось. Индексы = [0]");
    }

    @Test
    void testLargeFile() throws IOException {
        String fileName = "test_large.txt";
        ArrayList<Long> expected = new ArrayList<>();
        expected.add(4_000_000_000L);

        createTestFile(fileName, "");

        try (FileWriter writer = new FileWriter(fileName)) {
            for (long i = 0L; i < 500_000_000L; i++) {
                writer.write("无知很酷无知很酷");
            }
            writer.write("Hello");
        } catch (IOException e) {
            System.err.println("File write error");
            e.printStackTrace();
        }

        List<Long> res = SubstringSearch.find(fileName, "Hello");
        Assertions.assertEquals(expected, res);
    }
}
