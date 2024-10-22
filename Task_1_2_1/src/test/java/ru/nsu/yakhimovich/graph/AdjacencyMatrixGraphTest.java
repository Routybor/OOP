package ru.nsu.yakhimovich.graph;

import org.junit.jupiter.api.BeforeEach;

/**
 *
 */
public class AdjacencyMatrixGraphTest extends AbstractGraphTest {
    @BeforeEach
    @Override
    public void setUp() {
        graph = new AdjacencyMatrixGraph(5);
    }

    @Override
    protected Graph createGraph() {
        return new AdjacencyMatrixGraph(5);
    }

    @Override
    protected Graph createGraphWithCapacity() {
        return new AdjacencyMatrixGraph(3);
    }
}
