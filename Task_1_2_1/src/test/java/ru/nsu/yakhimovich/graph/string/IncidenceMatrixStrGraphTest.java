package ru.nsu.yakhimovich.graph.string;

import org.junit.jupiter.api.BeforeEach;
import ru.nsu.yakhimovich.graph.Graph;
import ru.nsu.yakhimovich.graph.IncidenceMatrixGraph;

/**
 * Использование абстрактных тестов для реализации через матрицу инцидентности.
 */
public class IncidenceMatrixStrGraphTest extends AbstractStrGraphTest {
    @BeforeEach
    @Override
    public void setUp() {
        graph = new IncidenceMatrixGraph<>(5, 5);
    }

    @Override
    protected Graph<String> createGraph() {
        return new IncidenceMatrixGraph<>(5, 5);
    }

    @Override
    protected Graph<String> createGraphWithCapacity() {
        return new IncidenceMatrixGraph<>(3, 3);
    }
}
