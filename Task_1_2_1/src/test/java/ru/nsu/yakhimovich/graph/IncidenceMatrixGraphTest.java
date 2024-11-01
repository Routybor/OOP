package ru.nsu.yakhimovich.graph;

import org.junit.jupiter.api.BeforeEach;

/**
 * Использование абстрактных тестов для реализации через матрицу инцидентности.
 */
public class IncidenceMatrixGraphTest extends AbstractGraphTest<String> {
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
