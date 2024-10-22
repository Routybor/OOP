package ru.nsu.yakhimovich.graph;

import java.io.IOException;
import java.util.*;

/**
 * Реализация графа
 */
public interface Graph {

    void addVertex(String vertex);

    void removeVertex(String vertex);

    void addEdge(String fromVertex, String toVertex);

    void removeEdge(String fromVertex, String toVertex);

    List<String> getNeighbors(String vertex);

    void readFromFile(String fileName) throws IOException;

    default List<String> topologicalSort() {
        List<String> sortedList = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        for (String vertex : getVertices()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }
        while (!stack.isEmpty()) {
            sortedList.add(stack.pop());
        }
        return sortedList;
    }

    default void topologicalSortUtil(String vertex, Set<String> visited, Stack<String> stack) {
        visited.add(vertex);
        for (String neighbor : getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        stack.push(vertex);
    }

    List<String> getVertices();
}
