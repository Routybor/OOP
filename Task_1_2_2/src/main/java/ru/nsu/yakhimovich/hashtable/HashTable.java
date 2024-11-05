package ru.nsu.yakhimovich.hashtable;

import java.util.*;

/**
 * Параметризованная хеш-таблица.
 * Поддерживает основные операции. (добавления, поиска, удаления, обновления значений по ключу,
 * итерирование, сравнение с другой таблицей)
 *
 * @param <K> Тип ключей
 * @param <V> Тип значений
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private List<Entry<K, V>>[] table;
    private int size;
    private int cap;
    private int modCnt;

    /**
     * Создание пустой хеш-таблицы с дефолтной емкостью.
     */
    public HashTable() {
        this.cap = DEFAULT_CAPACITY;
        this.table = new LinkedList[cap];
        this.size = 0;
        this.modCnt = 0;
    }

    /**
     * Вложенный класс для представления пар ключ-значение в хеш-таблице.
     *
     * @param <K> Тип ключей
     * @param <V> Тип значений
     */
    public static class Entry<K, V> {
        K key;
        V val;

        /**
         * Создание новой пары ключ-значение.
         *
         * @param key   Ключ
         * @param val Значение
         */
        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        /**
         * Возвращение ключа.
         *
         * @return Ключ
         */
        public K getKey() {
            return key;
        }

        /**
         * Возвращение значения.
         *
         * @return Значение
         */
        public V getValue() {
            return val;
        }

        /**
         * Возвращение строкового представления пары ключ-значение.
         *
         * @return Строковое представление пары ключ-значение
         */
        @Override
        public String toString() {
            return key + "=" + val;
        }
    }

    /**
     * Вычисление хеша для данного ключа.
     *
     * @param key Ключ
     * @return Индекс в массиве
     */
    private int hash(K key) {
        return Math.abs(key.hashCode() % cap);
    }

    /**
     * Добавление пары ключ-значение в хеш-таблицу.
     * Если ключ уже существует -> обновление его значение.
     *
     * @param key   Ключ
     * @param val Значение
     */
    public void put(K key, V val) {
        int idx = hash(key);
        if (table[idx] == null) {
            table[idx] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[idx]) {
            if (entry.key.equals(key)) {
                entry.val = val;
                return;
            }
        }
        table[idx].add(new Entry<>(key, val));
        size++;
        modCnt++;
        if (size > cap * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Возвращение значения по данному ключу.
     *
     * @param key Ключ
     * @return Значение или null, если ключ не найден
     */
    public V get(K key) {
        int idx = hash(key);
        if (table[idx] != null) {
            for (Entry<K, V> entry : table[idx]) {
                if (entry.key.equals(key)) {
                    return entry.val;
                }
            }
        }
        return null;
    }

    /**
     * Удаление ПАРЫ ключ-значение по заданному ключу.
     *
     * @param key Ключ
     */
    public void remove(K key) {
        int idx = hash(key);
        if (table[idx] != null) {
            Iterator<Entry<K, V>> iterator = table[idx].iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.key.equals(key)) {
                    iterator.remove();
                    size--;
                    modCnt++;
                    return;
                }
            }
        }
    }

    /**
     * Обновление значения для данного ключа.
     *
     * @param key   Ключ
     * @param val Новое значение
     */
    public void update(K key, V val) {
        put(key, val);
    }

    /**
     * Проверка на содержание в таблице указанного ключа.
     *
     * @param key Ключ
     * @return true, если ключ содержится в таблице, иначе false
     */
    public boolean containsKey(K key) {
        int idx = hash(key);
        if (table[idx] != null) {
            for (Entry<K, V> entry : table[idx]) {
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Возвращение итератора по элементам хеш-таблицы.
     * (с защитой от модификаций).
     *
     * @return Итератор по элементам таблицы
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final int expectedModCnt = modCnt;
            private int curBucket = 0;
            private Iterator<Entry<K, V>> bucketIter = (table[curBucket] == null)
                    ? null : table[curBucket].iterator();

            private void checkForModification() {
                if (modCnt != expectedModCnt) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public boolean hasNext() {
                checkForModification();
                while ((bucketIter == null
                        || !bucketIter.hasNext())
                        && curBucket < table.length - 1) {
                    curBucket++;
                    bucketIter = (table[curBucket] == null) ? null : table[curBucket].iterator();
                }
                return bucketIter != null && bucketIter.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                checkForModification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return bucketIter.next();
            }
        };
    }

    /**
     * Проверяет равенство с другой хеш-таблицей.
     *
     * @param other Другая хеш-таблица для сравнения
     * @return true, если таблицы равны, иначе false
     */
    public boolean equals(HashTable<K, V> other) {
        if (this.size != other.size) {
            return false;
        }
        for (Entry<K, V> entry : this) {
            V otherValue = other.get(entry.key);
            if (!Objects.equals(entry.val, otherValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращение строковое представление хеш-таблицы.
     *
     * @return Строковое представление таблицы
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < cap; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    sb.append(entry.key).append("=").append(entry.val).append(", ");
                }
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Увеличивает размер таблицы и перераспределяет элементы для поддержания
     * оптимальной загрузки.
     */
    private void resize() {
        int newCap = cap * 2;
        List<Entry<K, V>>[] newTable = new LinkedList[newCap];
        for (int i = 0; i < cap; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    int idx = Math.abs(entry.key.hashCode() % newCap);
                    if (newTable[idx] == null) {
                        newTable[idx] = new LinkedList<>();
                    }
                    newTable[idx].add(new Entry<>(entry.key, entry.val));
                }
            }
        }
        table = newTable;
        cap = newCap;
        modCnt++;
    }
}
