package ru.nsu.yakhimovich.hashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(1, hashTable.get("HAHA"));
        assertEquals(2, hashTable.get("HOH"));
        assertEquals(3, hashTable.get("BLOB"));
        assertNull(hashTable.get("nananan"));
    }

    @Test
    void testUpdate() {
        hashTable.put("HAHA", 1);
        hashTable.update("HAHA", 1.1);
        assertEquals(1.1, hashTable.get("HAHA"));
    }

    @Test
    void testRemove() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        hashTable.remove("HAHA");
        assertNull(hashTable.get("HAHA"));
        assertEquals(2, hashTable.get("HOH"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("HAHA", 1);
        assertTrue(hashTable.containsKey("HAHA"));
        assertFalse(hashTable.containsKey("HOH"));
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }
        for (int i = 0; i < 20; i++) {
            assertEquals(i, hashTable.get("key" + i));
        }
    }

    @Test
    void testEquals() {
        HashTable<String, Number> difTable = new HashTable<>();
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        difTable.put("HAHA", 1);
        difTable.put("HOH", 2);
        assertTrue(hashTable.equals(difTable));
        difTable.put("BLOB", 3);
        assertFalse(hashTable.equals(difTable));
    }

    @Test
    void testToString() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        String output = hashTable.toString();
        assertTrue(output.contains("HAHA=1"));
        assertTrue(output.contains("HOH=2"));
    }

    @Test
    void testConcurrentModificationException() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        Iterator<HashTable.Entry<String, Number>> iterator = hashTable.iterator();
        hashTable.put("BLOB", 3);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testIteration() {
        hashTable.put("HAHA", 1);
        hashTable.put("HOH", 2);
        hashTable.put("BLOB", 3);
        int count = 0;
        for (HashTable.Entry<String, Number> entry : hashTable) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testCollisionHandling() {
        hashTable.put("Aa", 1);
        hashTable.put("BB", 2);
        assertEquals(1, hashTable.get("Aa"));
        assertEquals(2, hashTable.get("BB"));
    }
}
