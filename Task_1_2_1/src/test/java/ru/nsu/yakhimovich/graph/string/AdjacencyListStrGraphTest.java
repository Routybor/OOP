package ru.nsu.yakhimovich.graph.string;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.yakhimovich.graph.AdjacencyListGraph;
import ru.nsu.yakhimovich.graph.Graph;

/**
 * Использование абстрактных тестов для реализации через список смежности.
 */
public class AdjacencyListStrGraphTest extends AbstractStrGraphTest {

    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Override
    protected Graph<String> createGraph() {
        return new AdjacencyListGraph<>();
    }

    @Override
    protected Graph<String> createGraphWithCapacity() {
        return new AdjacencyListGraph<>();
    }
}
