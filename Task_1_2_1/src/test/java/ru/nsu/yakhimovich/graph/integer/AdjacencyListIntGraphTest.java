package ru.nsu.yakhimovich.graph.integer;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.yakhimovich.graph.AdjacencyListGraph;
import ru.nsu.yakhimovich.graph.Graph;

/**
 * Использование абстрактных тестов для реализации через список смежности.
 */
public class AdjacencyListIntGraphTest extends AbstractIntGraphTest {

    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Override
    protected Graph<Integer> createGraph() {
        return new AdjacencyListGraph<>();
    }

    @Override
    protected Graph<Integer> createGraphWithCapacity() {
        return new AdjacencyListGraph<>();
    }
}
