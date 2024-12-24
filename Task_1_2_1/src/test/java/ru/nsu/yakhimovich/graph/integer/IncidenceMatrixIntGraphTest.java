package ru.nsu.yakhimovich.graph.integer;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.yakhimovich.graph.Graph;
import ru.nsu.yakhimovich.graph.IncidenceMatrixGraph;

/**
 * Использование абстрактных тестов для реализации через матрицу инцидентности.
 */
public class IncidenceMatrixIntGraphTest extends AbstractIntGraphTest {
    @BeforeEach
    @Override
    public void setUp() {
        graph = new IncidenceMatrixGraph<>(5, 5);
    }

    @Override
    protected Graph<Integer> createGraph() {
        return new IncidenceMatrixGraph<>(5, 5);
    }

    @Override
    protected Graph<Integer> createGraphWithCapacity() {
        return new IncidenceMatrixGraph<>(3, 3);
    }
}
