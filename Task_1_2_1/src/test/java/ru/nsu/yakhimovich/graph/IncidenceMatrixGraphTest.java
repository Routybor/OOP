package ru.nsu.yakhimovich.graph;

import org.junit.jupiter.api.BeforeEach;

/**
 *
 */
public class IncidenceMatrixGraphTest extends AbstractGraphTest {
    @BeforeEach
    @Override
    public void setUp() {
        graph = new IncidenceMatrixGraph(5, 5);
    }

    @Override
    protected Graph createGraph() {
        return new IncidenceMatrixGraph(5, 5);
    }

    @Override
    protected Graph createGraphWithCapacity() {
        return new IncidenceMatrixGraph(3, 3);
    }
}
