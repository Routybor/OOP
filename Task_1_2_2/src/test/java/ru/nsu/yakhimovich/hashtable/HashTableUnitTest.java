package ru.nsu.yakhimovich.hashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Юнит тесты для хэш-таблиц.
 */
class HashTableUnitTest {
    private HashTable<String, Number> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        hashTable.put("BLOB", 3);
        Assertions.assertEquals(1, hashTable.get("HAHA"));
        Assertions.assertEquals(2, hashTable.get("HOH"));
        Assertions.assertEquals(3, hashTable.get("BLOB"));
        Assertions.assertNull(hashTable.get("nananan"));
    }

    @Test
    void testUpdate() {
        hashTable.put("HAHA", 1);
        hashTable.update("HAHA", 1.1);
        Assertions.assertEquals(1.1, hashTable.get("HAHA"));
    }

    @Test
    void testRemove() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        hashTable.remove("HAHA");
        Assertions.assertNull(hashTable.get("HAHA"));
        Assertions.assertEquals(2, hashTable.get("HOH"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("HAHA", 1);
        Assertions.assertTrue(hashTable.containsKey("HAHA"));
        Assertions.assertFalse(hashTable.containsKey("HOH"));
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }
        for (int i = 0; i < 20; i++) {
            Assertions.assertEquals(i, hashTable.get("key" + i));
        }
    }

    @Test
    void testEquals() {
        HashTable<String, Number> difTable = new HashTable<>();
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        difTable.put("HAHA", 1);
        difTable.put("HOH", 2);
        Assertions.assertTrue(hashTable.equals(difTable));
        difTable.put("BLOB", 3);
        Assertions.assertFalse(hashTable.equals(difTable));
    }

    @Test
    void testToString() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        String output = hashTable.toString();
        Assertions.assertTrue(output.contains("HAHA=1"));
        Assertions.assertTrue(output.contains("HOH=2"));
    }

    @Test
    void testConcurrentModificationException() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        Iterator<HashTable.Entry<String, Number>> iterator = hashTable.iterator();
        hashTable.put("BLOB", 3);
        Assertions.assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testIteration() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        hashTable.put("BLOB", 3);
        int count = 0;
        for (HashTable.Entry<String, Number> entry : hashTable) {
            Assertions.assertNotNull(entry.getKey());
            Assertions.assertNotNull(entry.getValue());
            count++;
        }
        Assertions.assertEquals(3, count);
    }

    @Test
    void testCollisionHandling() {
        hashTable.put("Aa", 1);
        hashTable.put("BB", 2);
        Assertions.assertEquals(1, hashTable.get("Aa"));
        Assertions.assertEquals(2, hashTable.get("BB"));
    }
}
