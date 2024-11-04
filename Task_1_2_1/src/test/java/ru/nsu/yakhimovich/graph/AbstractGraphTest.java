package ru.nsu.yakhimovich.graph;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Абстрактные юнит-тесты для графа.
 *
 * @param <T> тип вершин в графе
 */
public abstract class AbstractGraphTest<T> {
    protected Graph<T> graph;

    /**
     * Преобразование строки в объект типа T.
     *
     * @param s строка для преобразования
     * @return объект типа T
     */
    private T parse(String s) {
        return (T) s; // Simple identity function for String type
    }

    @BeforeEach
    public abstract void setUp();

    @Test
    public void testRemoveVertex() {
        graph.addVertex(parse("A"));
        graph.addVertex(parse("B"));
        graph.addEdge(parse("A"), parse("B"));
        graph.removeVertex(parse("B"));
        Assertions.assertEquals(0, graph.getNeighbors(parse("A")).size());
    }

    @Test
    public void testAddEdge() {
        graph.addVertex(parse("A"));
        graph.addVertex(parse("B"));
        graph.addEdge(parse("A"), parse("B"));
        Assertions.assertEquals(List.of(parse("B")), graph.getNeighbors(parse("A")));
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex(parse("A"));
        graph.addVertex(parse("B"));
        graph.addEdge(parse("A"), parse("B"));
        graph.removeEdge(parse("A"), parse("B"));
        Assertions.assertEquals(List.of(), graph.getNeighbors(parse("A")));
    }

    @Test
    public void testTopologicalSort() {
        graph.addVertex(parse("A"));
        graph.addVertex(parse("B"));
        graph.addVertex(parse("C"));
        graph.addEdge(parse("A"), parse("B"));
        graph.addEdge(parse("B"), parse("C"));
        List<T> sorted = graph.topologicalSort();
        Assertions.assertEquals(Arrays.asList(parse("A"), parse("B"), parse("C")), sorted);
    }

    @Test
    public void testGraphEquality() {
        Graph<T> graph1 = createGraph();
        graph1.addVertex(parse("A"));
        graph1.addVertex(parse("B"));
        graph1.addEdge(parse("A"), parse("B"));

        Graph<T> graph2 = createGraph();
        graph2.addVertex(parse("A"));
        graph2.addVertex(parse("B"));
        graph2.addEdge(parse("A"), parse("B"));

        boolean isEqual = graph1.equals(graph2);
        Assertions.assertTrue(isEqual);
    }

    @Test
    public void testGraphInequality() {
        Graph<T> graph1 = createGraph();
        graph1.addVertex(parse("A"));
        graph1.addVertex(parse("B"));
        graph1.addEdge(parse("A"), parse("B"));

        Graph<T> graph2 = createGraph();
        graph2.addVertex(parse("A"));
        graph2.addVertex(parse("B"));
        graph2.addEdge(parse("B"), parse("A"));

        boolean isEqual = graph1.equals(graph2);
        Assertions.assertFalse(isEqual);
    }

    @Test
    public void testToString() {
        graph.addVertex(parse("A"));
        graph.addVertex(parse("B"));
        graph.addEdge(parse("A"), parse("B"));

        String expectedString = "A: B \nB: \n";
        Assertions.assertEquals(expectedString, graph.toString());
    }

    @Test
    public void testReadFromFile() throws IOException {
        graph.readFromFile("src/test/resources/test.txt", this::parse);

        Assertions.assertEquals(List.of(parse("B")), graph.getNeighbors(parse("A")));
        Assertions.assertEquals(List.of(parse("C")), graph.getNeighbors(parse("B")));
        Assertions.assertEquals(List.of(parse("D")), graph.getNeighbors(parse("C")));
        Assertions.assertEquals(List.of(), graph.getNeighbors(parse("D")));
    }

    @Test
    public void testOvercapacity() {
        Graph<T> graph1 = createGraphWithCapacity();
        graph1.addVertex(parse("1"));
        graph1.addVertex(parse("2"));
        graph1.addVertex(parse("3"));
        graph1.addVertex(parse("4"));
        graph1.addEdge(parse("1"), parse("2"));
        graph1.addEdge(parse("1"), parse("3"));
        graph1.addEdge(parse("2"), parse("4"));
        graph1.addEdge(parse("3"), parse("2"));
        graph1.addEdge(parse("3"), parse("4"));
        Assertions.assertEquals(List.of(parse("1"), parse("3"),
                parse("2"), parse("4")), graph1.topologicalSort());
    }

    /**
     * Создание нового экземпляра графа.
     *
     * @return новый экземпляр графа
     */
    protected abstract Graph<T> createGraph();

    /**
     * Создание нового экземпляра графа с установленной емкостью.
     *
     * @return новый экземпляр графа с емкостью
     */
    protected abstract Graph<T> createGraphWithCapacity();

}
