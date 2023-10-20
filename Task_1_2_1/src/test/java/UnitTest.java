import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit Tests.
 */
public class UnitTest {
    /**
     * Basic Tree from example.
     */
    public static Tree<String> sampleTree() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        b.remove();
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        return tree;
    }

    @Test
    void test1() {
        Tree<String> testTree1 = sampleTree();
        List<String> fromDfs = new ArrayList<>();
        Iterator<String> iterator = testTree1.iteratorDFS();
        while (iterator.hasNext()) {
            fromDfs.add(iterator.next());
        }
        System.out.print(fromDfs);
        String[] arr = new String[]{"R1", "R2", "D", "C", "A"};
        if (arr.length != fromDfs.size()) {
            Assertions.fail();
        }
        for (int i = 0; i < arr.length; i++) {
            if (!Objects.equals(fromDfs.get(i), arr[i])) {
                Assertions.fail();
            }
        }
    }

    @Test
    void test2() {
        Tree<String> testTree1 = sampleTree();
        List<String> fromBfs = new ArrayList<>();
        Iterator<String> iterator = testTree1.iteratorBFS();
        while (iterator.hasNext()) {
            fromBfs.add(iterator.next());
        }
        String[] arr = new String[]{"R1", "A", "R2", "C", "D"};
        if (arr.length != fromBfs.size()) {
            Assertions.fail();
        }
        for (int i = 0; i < arr.length; i++) {
            if (!Objects.equals(fromBfs.get(i), arr[i])) {
                Assertions.fail();
            }
        }
    }

    @Test()
    void test3() {
        Tree<String> testTree1 = sampleTree();
        Iterator<String> interpreter = testTree1.iteratorDFS();
        testTree1.addChild("DFS");
        assertThrows(ConcurrentModificationException.class, interpreter::next);
    }

    @Test()
    void test4() {
        Tree<String> testTree1 = sampleTree();
        Iterator<String> interpreter = testTree1.iteratorBFS();
        testTree1.addChild("BFS");
        assertThrows(ConcurrentModificationException.class, interpreter::next);
    }

    @Test
    void test5() {
        Tree<String> testTree1 = sampleTree();
        Tree<String> testTree2 = new Tree<>("R1");
        testTree2.addChild("A");
        Tree<String> subtree2 = new Tree<>("R2");
        subtree2.addChild("C");
        subtree2.addChild("D");
        testTree2.addChild(subtree2);
        assertEquals(testTree1, testTree2);
    }

    @Test
    void test6() {
        Tree<String> testTree1 = new Tree<>("R1");
        var a = testTree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        b.remove();
        subtree.addChild("D");
        testTree1.addChild(subtree);
        assertNotEquals(testTree1, subtree);
    }

    @Test
    void test7() {
        Tree<String> testTree1 = sampleTree();
        Tree<String> testTree2 = sampleTree();
        testTree1.addChild("ANOTHER_ONE");
        testTree2.addChild("AND_ANOTHER_ONE");
        assertNotEquals(testTree1, testTree2);
    }

    @Test
    void test8() {
        Tree<String> testTree1 = new Tree<>("R1");
        var a = testTree1.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        testTree1.addChild(subtree);
        assertNotEquals(testTree1, null);
    }

    @Test
    void test9() {
        Tree<String> testTree1 = new Tree<>("VOID");
        testTree1.remove();
        Tree<String> testTree2 = new Tree<>("VOID");
        testTree2.remove();
        assertEquals(testTree1, testTree2);
    }

    @Test
    void test10() {
        Tree<String> testTree1 = sampleTree();
        Tree<String> testTree2 = sampleTree();
        testTree1.addChild("ANOTHER_ONE");
        assertNotEquals(testTree1, testTree2);
    }

    @Test
    void test11() {
        Tree<String> testTree1 = sampleTree();
        Tree<String> testTree2 = sampleTree();
        testTree1.addChild("ANOTHER_ONE");
        testTree1.removeChild("ANOTHER_ONE");
        assertEquals(testTree1, testTree2);
    }

    @Test
    void test12() {
        Tree<String> testTree1 = sampleTree();
        Tree<String> testTree2 = sampleTree();
        testTree1.addChild("ANOTHER_ONE");
        testTree1.removeChild("WHAT");
        assertNotEquals(testTree1, testTree2);
    }

    @Test
    void test13() {
        Tree<String> testTree1 = sampleTree();
        Iterator<String> iterator = testTree1.iteratorDFS();
        while (iterator.hasNext()) {
            iterator.next();
        }
        try {
            iterator.next();
        } catch (NoSuchElementException ignored) {

        }
    }

    @Test
    void test14() {
        Tree<String> testTree1 = sampleTree();
        Iterator<String> iterator = testTree1.iteratorBFS();
        while (iterator.hasNext()) {
            iterator.next();
        }
        try {
            iterator.next();
        } catch (NoSuchElementException ignored) {

        }
    }
}
