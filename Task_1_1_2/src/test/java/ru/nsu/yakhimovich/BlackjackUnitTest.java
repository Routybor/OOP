package ru.nsu.yakhimovich;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

/**
 * Небольшой тест для проверки на функционирование кода.
 */
public class BlackjackUnitTest {
    @Test
    void mainTest() {
        // Эмуляция ввода из консоли
        String input = "1\n0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Ход игры
        int[] score = new int[2];
        for (int i = 0; i < 10; i++) {
            // Переустановить входную строку для программы
            in.reset();

            Blackjack game = new Blackjack();
            game.startRound(1, score);
        }
    }
}

