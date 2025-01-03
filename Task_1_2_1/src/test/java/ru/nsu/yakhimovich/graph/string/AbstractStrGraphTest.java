package ru.nsu.yakhimovich.graph.string;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.yakhimovich.graph.Graph;

/**
 * Теста для графа.
 */
public abstract class AbstractStrGraphTest {
    protected Graph<String> graph;

    @BeforeEach
    public abstract void setUp();

    @Test
    public void testRemoveVertex() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.removeVertex("B");
        Assertions.assertEquals(0, graph.getNeighbors("A").size());
    }

    @Test
    public void testAddEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        Assertions.assertEquals(List.of("B"), graph.getNeighbors("A"));
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.removeEdge("A", "B");
        Assertions.assertEquals(List.of(), graph.getNeighbors("A"));
    }

    @Test
    public void testTopologicalSort() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        List<String> sorted = graph.topologicalSort();
        Assertions.assertEquals(Arrays.asList("A", "B", "C"), sorted);
    }

    @Test
    public void testGraphEquality() {
        Graph<String> graph1 = createGraph();
        graph1.addVertex("A");
        graph1.addVertex("B");
        graph1.addEdge("A", "B");

        Graph<String> graph2 = createGraph();
        graph2.addVertex("A");
        graph2.addVertex("B");
        graph2.addEdge("A", "B");

        boolean isEqual = graph1.equals(graph2);
        Assertions.assertTrue(isEqual);
    }

    @Test
    public void testGraphInequality() {
        Graph<String> graph1 = createGraph();
        graph1.addVertex("A");
        graph1.addVertex("B");
        graph1.addEdge("A", "B");

        Graph<String> graph2 = createGraph();
        graph2.addVertex("A");
        graph2.addVertex("B");
        graph2.addEdge("B", "A");

        boolean isEqual = graph1.equals(graph2);
        Assertions.assertFalse(isEqual);
    }

    @Test
    public void testToString() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        String expectedString = "A: B \nB: \n";
        Assertions.assertEquals(expectedString, graph.toString());
    }

    @Test
    public void testReadFromFile() throws IOException {
        graph.readFromFile("test1.txt", s -> s);

        Assertions.assertEquals(List.of("B"), graph.getNeighbors("A"));
        Assertions.assertEquals(List.of("C"), graph.getNeighbors("B"));
        Assertions.assertEquals(List.of("D"), graph.getNeighbors("C"));
        Assertions.assertEquals(List.of(), graph.getNeighbors("D"));
    }

    @Test
    public void testOvercapacity() {
        Graph<String> graph1 = createGraphWithCapacity();
        graph1.addVertex("1");
        graph1.addVertex("2");
        graph1.addVertex("3");
        graph1.addVertex("4");
        graph1.addEdge("1", "2");
        graph1.addEdge("1", "3");
        graph1.addEdge("2", "4");
        graph1.addEdge("3", "2");
        graph1.addEdge("3", "4");
        Assertions.assertEquals(List.of("1", "3", "2", "4"), graph1.topologicalSort());
    }

    /**
     * Создание нового экземпляра графа.
     *
     * @return новый экземпляр графа
     */
    protected abstract Graph<String> createGraph();

    /**
     * Создание нового экземпляра графа с установленной емкостью.
     *
     * @return новый экземпляр графа с емкостью
     */
    protected abstract Graph<String> createGraphWithCapacity();

}
