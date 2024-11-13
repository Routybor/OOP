package ru.nsu.yakhimovich.graph.integer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.yakhimovich.graph.Graph;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Теста для графа.
 */
public abstract class AbstractIntGraphTest {
    protected Graph<Integer> graph;

    @BeforeEach
    public abstract void setUp();

    @Test
    public void testRemoveVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeVertex(2);
        Assertions.assertEquals(0, graph.getNeighbors(1).size());
    }

    @Test
    public void testAddEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        Assertions.assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);
        Assertions.assertEquals(List.of(), graph.getNeighbors(1));
    }

    @Test
    public void testTopologicalSort() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        List<Integer> sorted = graph.topologicalSort();
        Assertions.assertEquals(Arrays.asList(1, 2, 3), sorted);
    }

    @Test
    public void testGraphEquality() {
        Graph<Integer> graph1 = createGraph();
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        Graph<Integer> graph2 = createGraph();
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2);

        boolean isEqual = graph1.equals(graph2);
        Assertions.assertTrue(isEqual);
    }

    @Test
    public void testGraphInequality() {
        Graph<Integer> graph1 = createGraph();
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        Graph<Integer> graph2 = createGraph();
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(2, 1);

        boolean isEqual = graph1.equals(graph2);
        Assertions.assertFalse(isEqual);
    }

    @Test
    public void testToString() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        String expectedString = "1: 2 \n2: \n";
        Assertions.assertEquals(expectedString, graph.toString());
    }

    @Test
    public void testReadFromFile() throws IOException {
        graph.readFromFile("src/test/resources/test2.txt", Integer::valueOf);

        Assertions.assertEquals(List.of(2), graph.getNeighbors(1));
        Assertions.assertEquals(List.of(3), graph.getNeighbors(2));
        Assertions.assertEquals(List.of(4), graph.getNeighbors(3));
        Assertions.assertEquals(List.of(), graph.getNeighbors(4));
    }

    @Test
    public void testOvercapacity() {
        Graph<Integer> graph1 = createGraphWithCapacity();
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addVertex(3);
        graph1.addVertex(4);
        graph1.addEdge(1, 2);
        graph1.addEdge(1, 3);
        graph1.addEdge(2, 4);
        graph1.addEdge(3, 2);
        graph1.addEdge(3, 4);
        Assertions.assertEquals(List.of(1, 3, 2, 4), graph1.topologicalSort());
    }

    /**
     * Создание нового экземпляра графа.
     *
     * @return новый экземпляр графа
     */
    protected abstract Graph<Integer> createGraph();

    /**
     * Создание нового экземпляра графа с установленной емкостью.
     *
     * @return новый экземпляр графа с емкостью
     */
    protected abstract Graph<Integer> createGraphWithCapacity();

}
