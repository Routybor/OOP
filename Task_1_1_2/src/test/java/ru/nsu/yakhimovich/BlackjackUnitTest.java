package ru.nsu.yakhimovich;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Небольшой тест для проверки на функционирование кода.
 */
public class BlackjackUnitTest {
    @Test
    void mainTest() {
        // Эмуляция ввода из консоли
        String input = "1\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Ход игры
        int[] score = new int[2];
        Blackjack game = new Blackjack();
        game.startRound(1, score);
    }
}
