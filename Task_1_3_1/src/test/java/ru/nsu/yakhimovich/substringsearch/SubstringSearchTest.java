package ru.nsu.yakhimovich.substringsearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


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
    private void createTestFile(String fileName, String content, int repeat) throws IOException {
        curTestFile = fileName; // Сохранение имя текущего файла для последующего удаления
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < repeat; i++) {
                writer.write(content);
            }
        }
    }

    @Test
    public void testFindSubstringMultipleOccurrences() throws IOException {
        String fileName = "test1.txt";
        createTestFile(fileName, "абракадабра", 1);

        List<Integer> result = SubstringSearch.find(fileName, "бра");
        Assertions.assertEquals(List.of(1, 8), result, "Ожидалось. Индексы = [1, 8]");
    }

    @Test
    public void testFindSubstringSingleOccurrence() throws IOException {
        String fileName = "test2.txt";
        createTestFile(fileName, "hello worm?", 1);

        List<Integer> result = SubstringSearch.find(fileName, "worm");
        Assertions.assertEquals(List.of(6), result, "Ожидалось. Индексы = [6]");
    }

    @Test
    public void testFindSubstringNoOccurrence() throws IOException {
        String fileName = "test3.txt";
        createTestFile(fileName, "abcdefg", 1);

        List<Integer> result = SubstringSearch.find(fileName, "xyz");
        Assertions.assertEquals(List.of(), result, "Ожидалось. Не найдено");
    }

    @Test
    public void testFindSubstringAtStart() throws IOException {
        String fileName = "test4.txt";
        createTestFile(fileName, "abra", 1);

        List<Integer> result = SubstringSearch.find(fileName, "abra");
        Assertions.assertEquals(List.of(0), result, "Ожидалось. Индексы = [0]");
    }

    @Test
    public void testFindLargeFile() throws IOException {
        String fileName = "large_test.txt";
        createTestFile(fileName, "无知很酷".repeat(20_000_000) + "ЮЮЮ", 100);

        List<Integer> result = SubstringSearch.find(fileName, "ЮЮЮ");
        Assertions.assertEquals(100, result.size(), "Ожидалось. 100 вхождений");
    }
}
