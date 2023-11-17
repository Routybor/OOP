import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Поиск вхождений подстроки в файле.
 */
public class Substring {
    /**
     * Ищет вхождения подстроки в файле.
     *
     * @param fileName  Имя файла.
     * @param substring Искомая подстрока.
     * @return Список индексов вхождений подстроки.
     * @throws IOException Возникает когда файл не найден.
     */
    public static List<Integer> find(String fileName, String substring) throws IOException {
        int i;
        // список индексов вхождений подстроки
        List<Integer> indexes = new ArrayList<>();
        // для поддержки UTF-8, из строки получается массив байтов
        byte[] substringBytes = substring.getBytes(StandardCharsets.UTF_8);
        byte[] buffer = new byte[10];

        // открывается поток для чтения файла
        try (var inputStream = Files.newInputStream(Path.of(fileName))) {
            int bytesRead;
            int indexesSize = 0;
            int totalBytesRead = 0;

            // файл считывается по частям в буфер
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // перебираются считанные байты в буфере
                for (i = 0; i < bytesRead; i++) {
                    // проверяется совпадение текущего байта с первым байтом подстроки
                    if (buffer[i] == substringBytes[indexesSize]) {
                        indexesSize++;
                        // найдено совпадение => продолжается проверка следующих байтов подстроки
                        if (indexesSize == substringBytes.length) {
                            // Найдено полное вхождение подстроки, сохраняется позицию
                            indexes.add(totalBytesRead + i - indexesSize + 1);
                            indexesSize = 0;
                        }
                    } else {
                        // если байты не совпадают, сбрасывается счетчик совпадений
                        indexesSize = 0;
                    }
                }
                totalBytesRead += bytesRead;
            }
        }

        return indexes;
    }

    /**
     * Пример использования функции.
     *
     * @param args Аргументы.
     */
    public static void main(String[] args) {
        try {
            List<Integer> result = find("src/main/resources/input.txt", "try");
            System.out.println("try = " + result);

            result = find("src/main/resources/input.txt", "хаха");
            System.out.println("xaxa = " + result);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}