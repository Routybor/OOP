package ru.nsu.yakhimovich;

/**
 * Класс для хранения и управления картами в руке игрока и дилера.
 */
public class Hand {
    private final Card[] cards;
    private int cardCount; // Для смещения индекса карт

    /**
     * Конструктор создает пустую руку, состоящую максимум из 10 карт.
     * Крайний случай - 1*4 + 2*4 + 3*3 = 21.
     */
    public Hand() {
        cards = new Card[10];
        cardCount = 0;
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card карта, которая добавляется в руку
     */
    public void addCard(Card card) {
        cards[cardCount++] = card;
    }

    /**
     * Вычисляет общую сумму очков в руке.
     * Если в руке есть туз, то его значение может быть 11/1.
     *
     * @return общая сумма очков в руке
     */
    public int getTotalValue() {
        int total = 0;
        int[] aces = new int[4]; // Массив для хранения индексов тузов
        int acesCount = 0;
        for (int i = 0; i < cardCount; i++) {
            if (cards[i].getName().startsWith("Туз")) {
                aces[acesCount++] = i;
            }
            total += cards[i].getPower();
        }
        for (int i = 0; i < acesCount; i++) {
            if (total > 21 && cards[aces[i]].getPower() == 11) {
                total -= 10;
                cards[aces[i]].setPower(1); // Обновляет значение туза для корректного отображения
            }
        }
        return total;
    }

    /**
     * Возвращает результат игры в зависимости от руки.
     * Если сумма больше 21 — проигрыш, если 21 — выигрыш, иначе продолжается игра.
     *
     * @return -1, если проигрыш (больше 21), 1, если выигрыш (21), 0 — продолжается игра
     */
    public int handResult() {
        int score = getTotalValue();
        if (score > 21) {
            return -1;
        } else if (score == 21) {
            return 1;
        }
        return 0;
    }

    /**
     * Возвращает строковое представление руки, где одна карта закрыта (для дилера).
     *
     * @return строка, представляющая руку с одной закрытой картой
     */
    public String closedHand() {
        String result = "";
        result += cards[0];
        result += ", <закрытая карта>";
        return "[" + result + "]";
    }

    /**
     * Возвращает закрытую карту дилера (она вторая в данных условиях).
     *
     * @return закрытая карта
     */
    public Card closedCard() {
        return cards[1];
    }

    /**
     * Возвращает строковое представление всех карт в руке.
     *
     * @return строка, представляющая карты в руке и общую сумму очков
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String separate = ", ";
        for (int i = 0; i < cardCount; i++) {
            if (i == cardCount - 1) {
                separate = "";
            }
            result.append(cards[i]).append(separate);
        }
        return "[" + result.toString().trim() + "]" + " > " + getTotalValue();
    }
}
