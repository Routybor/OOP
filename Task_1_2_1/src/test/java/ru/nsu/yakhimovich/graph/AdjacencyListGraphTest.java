package ru.nsu.yakhimovich.graph;

import org.junit.jupiter.api.BeforeEach;

/**
 * Использование абстрактных тестов для реализации через список смежности.
 */
public class AdjacencyListGraphTest extends AbstractGraphTest<String> {

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
