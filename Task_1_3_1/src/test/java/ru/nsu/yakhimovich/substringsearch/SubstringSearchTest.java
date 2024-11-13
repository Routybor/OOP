package ru.nsu.yakhimovich.substringsearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Тесты поиска подстроки.
 */
public class SubstringSearchTest {

    private String curTestFile;

    /**
     * Создание файла для теста.
     *
     * @param fileName имя файла
     * @param content  наполнение файла
     * @throws IOException ошибка создания/записи
     */
    private void createTestFile(String fileName, String content) throws IOException {
        curTestFile = fileName; // Сохранение имя текущего файла для последующего удаления
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }

    /**
     * Удаление файлов для тестов.
     */
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
        Assertions.assertEquals(List.of(1, 8), result, "Ожидалось. Индексы = [1, 8]");
    }

    @Test
    public void testFindSubstringSingleOccurrence() throws IOException {
        String fileName = "test2.txt";
        createTestFile(fileName, "hello world");

        List<Integer> result = SubstringSearch.find(fileName, "world");
        Assertions.assertEquals(List.of(6), result, "Ожидалось. Индексы = [6]");
    }

    @Test
    public void testFindSubstringNoOccurrence() throws IOException {
        String fileName = "test3.txt";
        createTestFile(fileName, "abcdefg");

        List<Integer> result = SubstringSearch.find(fileName, "xyz");
        Assertions.assertEquals(List.of(), result, "Ожидалось. Не найдено");
    }

    @Test
    public void testFindSubstringAtStart() throws IOException {
        String fileName = "test4.txt";
        createTestFile(fileName, "abra");

        List<Integer> result = SubstringSearch.find(fileName, "abra");
        Assertions.assertEquals(List.of(0), result, "Ожидалось. Индексы = [0]");
    }

    @Test
    public void testFindLargeFile() throws IOException {
        String fileName = "large_test.txt";
        createTestFile(fileName, "абракадабра".repeat(1000000));

        List<Integer> result = SubstringSearch.find(fileName, "бра");
        Assertions.assertEquals(2000000, result.size(), "Ожидалось. 200,000 вхождений");
    }
}
