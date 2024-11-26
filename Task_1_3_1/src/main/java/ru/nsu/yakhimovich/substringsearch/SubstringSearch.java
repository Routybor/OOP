package ru.nsu.yakhimovich.substringsearch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий поиск подстроки в файле.
 */
public class SubstringSearch {
    /**
     * Метод поиска подстроки в данном файле.
     *
     * @param fileName     данный файл
     * @param searchString строка, которую следует найти
     * @return массив позиций, на которых находится данная подстрока
     * @throws IOException ошибка чтения
     */
    public static List<Integer> find(String fileName, String searchString) throws IOException {
        List<Integer> positions = new ArrayList<>();
        int bufSize = 4194304;
        int charsRead;
        int curIdx;
        int startIdx;
        int start;
        char[] buffer = new char[bufSize];
        int overlap = searchString.length() - 1; // Перекрытие для разорванной подстроки
        StringBuilder previous = new StringBuilder(); // Для сохранения предыдущего блока

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            curIdx = 0;

            while ((charsRead = reader.read(buffer)) != -1) {
                String curBlock = new String(buffer, 0, charsRead);
                StringBuilder combinedBlock = new StringBuilder(previous).append(curBlock);

                startIdx = 0;
                while ((startIdx = combinedBlock.indexOf(searchString, startIdx)) != -1) {
                    positions.add(curIdx + startIdx - previous.length());
                    startIdx += 1; // Сдвиг для поиска следующего вхождения
                }

                // Сохранение последних символов для перекрытия
                start = Math.max(combinedBlock.length() - overlap, 0);
                previous = new StringBuilder(combinedBlock.substring(start));
                curIdx += charsRead;
            }
        }

        return positions;
    }
}
