package ru.nsu.yakhimovich;

/**
 * Класс, представляющий карту в игре.
 * Карта имеет название и силу (количество очков).
 */
public class Card {
    private final String name;
    private int power;

    /**
     * Конструктор создает карту с указанными названием и силой.
     *
     * @param name название карты (например, "Туз Черви")
     * @param power сила карты.
     */
    public Card(String name, int power) {
        this.name = name;
        this.power = power;
    }

    /**
     * Возвращает название карты.
     *
     * @return название карты
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает силу карты.
     *
     * @return сила карты
     */
    public int getPower() {
        return power;
    }

    /**
     * Устанавливает новое значение силы карты.
     * Служит это для того, чтобы изменять силу тузов на 1.
     * (Чтобы при использовании toString получали 1 а не 10.
     *
     * @param newPower новое значение силы карты
     */
    public void setPower(int newPower) {
        this.power = newPower;
    }

    /**
     * Возвращает строковое представление карты.
     * Формат = "Название карты (сила)".
     *
     * @return строка, представляющая карту
     */
    @Override
    public String toString() {
        return name + " (" + power + ")";
    }
}
