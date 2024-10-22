package ru.nsu.yakhimovich.graph;

import org.junit.jupiter.api.BeforeEach;

/**
 *
 */
public class AdjacencyListGraphTest extends AbstractGraphTest {
    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Override
    protected Graph createGraph() {
        return new AdjacencyListGraph();
    }

    @Override
    protected Graph createGraphWithCapacity() {
        return new AdjacencyListGraph();
    }
}
