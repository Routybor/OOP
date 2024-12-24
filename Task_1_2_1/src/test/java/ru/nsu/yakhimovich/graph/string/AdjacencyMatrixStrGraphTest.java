package ru.nsu.yakhimovich.graph.string;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.yakhimovich.graph.AdjacencyMatrixGraph;
import ru.nsu.yakhimovich.graph.Graph;

/**
 * Использование абстрактных тестов для реализации через матрицу смежности.
 */
public class AdjacencyMatrixStrGraphTest extends AbstractStrGraphTest {

    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyMatrixGraph<>(5);
    }

    @Override
    protected Graph<String> createGraph() {
        return new AdjacencyMatrixGraph<>(5);
    }

    @Override
    protected Graph<String> createGraphWithCapacity() {
        return new AdjacencyMatrixGraph<>(3);
    }
}
