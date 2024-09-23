package ru.nsu.yakhimovich;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит тесты.
 */
class BlackjackTest {
    private Blackjack blackjack;
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private int[] score;

    @BeforeEach
    void setUp() {
        blackjack = new Blackjack();
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        score = new int[2];
    }

    @Test
    void testDeckHas52Cards() {
        for (int i = 0; i < 52; i++) {
            assertNotNull(deck.pickCard());
        }
        assertNull(deck.pickCard());
    }

    @Test
    void testCardCreation() {
        Card card = new Card("Туз Черви", 11);
        assertEquals("Туз Черви", card.getName());
        assertEquals(11, card.getPower());

        card.setPower(1);
        assertEquals(1, card.getPower());
    }

    @Test
    void testHandAddCard() {
        playerHand.addCard(new Card("Десятка Пики", 10));
        playerHand.addCard(new Card("Туз Черви", 11));

        assertEquals(21, playerHand.getTotalValue());
        assertEquals("[Десятка Пики (10), Туз Черви (11)] > 21", playerHand.toString());
    }

    @Test
    void testHandResultWithAce() {
        playerHand.addCard(new Card("Десятка Пики", 10));
        playerHand.addCard(new Card("Туз Черви", 11));

        assertEquals(1, playerHand.handResult());
    }

    @Test
    void testHandBust() {
        playerHand.addCard(new Card("Десятка Пики", 10));
        playerHand.addCard(new Card("Десятка Черви", 10));
        playerHand.addCard(new Card("Двойка Трефы", 2));

        assertEquals(-1, playerHand.handResult());
    }

    @Test
    void testDealerTurn() {
        playerHand.addCard(new Card("Девятка Черви", 9));
        playerHand.addCard(new Card("Восьмерка Пики", 8));
        dealerHand.addCard(new Card("Десятка Буби", 10));
        dealerHand.addCard(new Card("Семерка Трефы", 7));

        assertEquals(17, dealerHand.getTotalValue());
        assertEquals(0, playerHand.handResult());
    }

    @Test
    void testPlayerTurnWin() {
        playerHand.addCard(new Card("Десятка Черви", 10));
        playerHand.addCard(new Card("Туз Пики", 11));

        assertEquals(1, playerHand.handResult());
    }

    @Test
    void testDealerTurnBust() {
        dealerHand.addCard(new Card("Десятка Буби", 10));
        dealerHand.addCard(new Card("Девятка Трефы", 9));
        dealerHand.addCard(new Card("Пятерка Черви", 5));

        assertEquals(-1, dealerHand.handResult());
    }

    @Test
    void testPause() {
        long start = System.currentTimeMillis();
        blackjack.pause(2);
        long end = System.currentTimeMillis();

        assertTrue(end - start >= 2000);
    }

    @Test
    void testHandClosedHand() {
        dealerHand.addCard(new Card("Десятка Пики", 10));
        dealerHand.addCard(new Card("Туз Черви", 11));

        assertEquals("[Десятка Пики (10), <закрытая карта>]", dealerHand.closedHand());
    }

    @Test
    void testStartRound() {
        int[] initialScore = score.clone();
        String input = "1\n0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        for (int i = 0; i < 10; i++) {
            in.reset();
            blackjack = new Blackjack();
            blackjack.startRound(i, score);
        }

        assertFalse(score[0] == initialScore[0] && score[1] == initialScore[1]);
    }

    @Test
    void testInitHands() {
        blackjack.initHands();
        playerHand = blackjack.getPlayerHand();
        dealerHand = blackjack.getDealerHand();

        assertTrue(playerHand.getTotalValue() >= 2);
        assertTrue(dealerHand.getTotalValue() >= 2);
    }

    @Test
    void testPlayerTurnSimulation() {
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes()));
        int result = blackjack.playerTurn();
        assertTrue(result >= -1 && result <= 1);
    }

    @Test
    void testDealerTurnSimulation() {
        int result = blackjack.dealerTurn();
        assertTrue(result >= -1 && result <= 1);
    }
}
