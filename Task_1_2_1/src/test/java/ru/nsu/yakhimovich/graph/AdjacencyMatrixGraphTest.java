package ru.nsu.yakhimovich.graph;

import org.junit.jupiter.api.BeforeEach;

/**
 * Использование абстрактных тестов для реализации через матрицу смежности.
 */
public class AdjacencyMatrixGraphTest extends AbstractGraphTest<String> {

    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyMatrixGraph<>(5); // Ensure to use the generic type
    }

    @Override
    protected Graph<String> createGraph() {
        return new AdjacencyMatrixGraph<>(5); // Ensure to use the generic type
    }

    @Override
    protected Graph<String> createGraphWithCapacity() {
        return new AdjacencyMatrixGraph<>(3); // Ensure to use the generic type
    }
}
