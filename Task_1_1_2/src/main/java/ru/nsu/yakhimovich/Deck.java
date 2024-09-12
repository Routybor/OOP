package ru.nsu.yakhimovich;

import java.util.Random;

/**
 * Класс, представляющий колоду карт для игры.
 * Содержит 52 карты, поддерживает перемешивание и раздачу карт.
 */
public class Deck {
    private final Card[] cards;
    private int currentCardIndex;

    /**
     * Конструктор создает колоду из 52 карт.
     * 4 масти: Черви, Буби, Трефы и Пики, каждая масть содержит 13 карт: от Двойки до Туза.
     * После создания колода АВТОМАТИЧЕСКИ перемешивается.
     */
    public Deck() {
        String[] suits = {"Черви", "Буби", "Трефы", "Пики"};
        String[] ranks = {"Двойка", "Тройка", "Четверка", "Пятерка", "Шестерка", "Семерка",
                          "Восьмерка", "Девятка", "Десятка", "Валет", "Королева", "Король", "Туз"};
        cards = new Card[52];
        int index = 0;
        int power;
        currentCardIndex = 0;

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                if (i == 12) { // Если это туз
                    power = 11;
                } else {
                    power = Math.min(i + 2, 10);
                }
                cards[index++] = new Card(ranks[i] + " " + suit, power);
            }
        }
        shuffle();
    }

    /**
     * Перемешивает колоду карт случайным образом.
     * Использует алгоритм Фишера-Йетса
     */
    public void shuffle() {
        Random rand = new Random();
        int index;
        Card temp;

        for (int i = cards.length - 1; i > 0; i--) {
            index = rand.nextInt(i + 1);
            temp = cards[i];
            cards[i] = cards[index];
            cards[index] = temp;
        }
    }

    /**
     * Раздает следующую карту из колоды.
     * После раздачи карта больше не доступна для раздачи.
     * Реализовано с помощью изменения индекса последней выбранной карты.
     *
     * @return следующая карта из колоды или null, если карты закончились
     */
    public Card pickCard() {
        if (currentCardIndex < cards.length) {
            return cards[currentCardIndex++];
        } else {
            return null; // Такое состояние не должно возникать в данных условиях
        }
    }
}
