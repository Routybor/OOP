package ru.nsu.yakhimovich.graph.integer;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.yakhimovich.graph.AdjacencyMatrixGraph;
import ru.nsu.yakhimovich.graph.Graph;

/**
 * Использование абстрактных тестов для реализации через матрицу смежности.
 */
public class AdjacencyMatrixIntGraphTest extends AbstractIntGraphTest {

    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyMatrixGraph<>(5);
    }

    @Override
    protected Graph<Integer> createGraph() {
        return new AdjacencyMatrixGraph<>(5);
    }

    @Override
    protected Graph<Integer> createGraphWithCapacity() {
        return new AdjacencyMatrixGraph<>(3);
    }
}
