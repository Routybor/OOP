package ru.nsu.yakhimovich;

import java.util.Scanner;

/**
 * Класс для реализации игры.
 * Содержит логику игры.
 */
public class Blackjack {
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;

    /**
     * Конструктор игры.
     * Инициализирует колоду карт, руки игрока и дилера.
     */
    public Blackjack() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    /**
     * Начинает раунд игры.
     * Раздает карты игроку и дилеру, инициирует ход игрока и дилера.
     *
     * @param round номер текущего раунда
     * @param score счет игры, первый элемент - игрок, второй - дилер
     */
    public void startRound(int round, int[] score) {
        String scoreResult;
        int playerResult, dealerResult;

        System.out.println("Добро пожаловать в Блэкджек!");
        System.out.printf("Раунд %d%nДилер раздал карты%n", round);
        initHands();
        System.out.printf("Ваши карты: %s%n", playerHand);
        System.out.printf("Карты дилера: %s%n", dealerHand.closedHand());
        playerResult = playerTurn();
        switch (playerResult) {
            case 0:
                dealerResult = dealerTurn();
                switch (dealerResult) {
                    case 1:
                        score[1]++;
                        break;
                    case -1:
                        score[0]++;
                        break;
                }
            case 1:
                score[0]++;
                break;
            case -1:
                score[1]++;
                break;
        }
        if (score[0] > score[1]) {
            scoreResult = "в вашу пользу";
        } else if (score[0] < score[1]) {
            scoreResult = "в пользу дилера";
        } else {
            scoreResult = "в пользу дружбы";
        }
        System.out.printf("Счет %s:%s %s.%n", score[0], score[1], scoreResult);
        pause(4);
        System.out.printf("%n%n%n%n%n%n%n%n%n");
    }

    /**
     * Инициализирует руки игрока и дилера, раздавая по две карты.
     */
    private void initHands() {
        playerHand.addCard(deck.pickCard());
        playerHand.addCard(deck.pickCard());
        dealerHand.addCard(deck.pickCard());
        dealerHand.addCard(deck.pickCard());
    }

    /**
     * Ход игрока.
     * Игрок либо взять еще одну карту, либо остановиться.
     *
     * @return результат хода игрока: -1 — проигрыш, 1 — выигрыш, 0 — продолжение игры
     */
    private int playerTurn() {
        int playerResult = 0;
        String decision;
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < 8; i++) { // 1*4 + 2*4 + 3*3 = 21 - (10 карт = макс)
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться.");
            decision = scan.nextLine();
            if (decision.equalsIgnoreCase("1")) {
                playerHand.addCard(deck.pickCard());
                playerResult = playerHand.handResult();
                System.out.printf("Ваши карты: %s%n", playerHand);
                if (playerResult == -1) {
                    System.out.println("\u001B[31mВы проиграли раунд!\033[0m");
                    break;

                } else if (playerResult == 1) {
                    System.out.println("\u001B[32mВы выиграли раунд!\033[0m");
                    break;

                }
            } else if (decision.equalsIgnoreCase("0")) {
                break;
            } else {
                System.out.println("Доступные варианты: “1”, “0”.");
            }
        }
        return playerResult;
    }

    /**
     * Ход дилера.
     * Если дилер превышает 21 очко, игрок выигрывает.
     * В противном случае сравниваются очки игрока и дилера.
     */
    private int dealerTurn() {
        int dealerResult = 0;

        System.out.printf("Ход дилера%n-------%n");
        System.out.printf("Дилер открывает карту %s%n", dealerHand.closedCard());
        System.out.printf("Карты дилера: %s%n", dealerHand);
        while (dealerHand.getTotalValue() < 17) {
            pause(3);
            Card openedCard = deck.pickCard();
            dealerHand.addCard(openedCard);
            System.out.printf("Дилер открывает карту %s%n", openedCard);
            System.out.printf("Карты дилера: %s%n", dealerHand);
        }
        if (dealerHand.handResult() == -1) {
            System.out.println("\u001B[32mВы выиграли раунд!\033[0m");
            dealerResult = -1;
        } else {
            int playerTotal = playerHand.getTotalValue();
            int dealerTotal = dealerHand.getTotalValue();
            if (playerTotal > dealerTotal) {
                System.out.println("\u001B[32mВы выиграли раунд!\033[0m");
                dealerResult = -1;
            } else if (playerTotal < dealerTotal) {
                System.out.println("\u001B[31mВы проиграли раунд!\033[0m");
                dealerResult = 1;
            } else {
                System.out.println("\u001B[33mНичья!\033[0m");
            }
        }
        return dealerResult;
    }

    /**
     * Пауза на указанное количество секунд.
     *
     * @param sec количество секунд для паузы
     */
    private void pause(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Точка входа в программу.
     * Запускает 10 раундов игры.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        int[] score = new int[2];
        for (int round = 1; round <= 10; round++) {
            Blackjack game = new Blackjack();
            game.startRound(round, score);
        }
    }
}
