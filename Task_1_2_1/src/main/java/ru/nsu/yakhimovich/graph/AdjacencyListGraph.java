package ru.nsu.yakhimovich.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Реализация графа через список смежности.
 */
public class AdjacencyListGraph implements Graph {
    private final Map<String, List<String>> adjacencyList; // Список смежности

    /**
     *
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Добавление вершины.
     *
     * @param vertex имя вершины
     */
    @Override
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Удаление вершины.
     *
     * @param vertex имя вершины
     */
    @Override
    public void removeVertex(String vertex) {
        adjacencyList.remove(vertex);
        for (List<String> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }

    /**
     * Добавление ребра.
     *
     * @param fromVertex имя вершины, от которой направлено ребро
     * @param toVertex   имя вершины, к которой направлено ребро
     */
    @Override
    public void addEdge(String fromVertex, String toVertex) {
        if (adjacencyList.containsKey(fromVertex) && adjacencyList.containsKey(toVertex)) {
            adjacencyList.get(fromVertex).add(toVertex);
        }
    }

    /**
     * Удаление ребра.
     *
     * @param fromVertex имя вершины, от которой направлено ребро
     * @param toVertex   имя вершины, к которой направлено ребро
     */
    @Override
    public void removeEdge(String fromVertex, String toVertex) {
        if (adjacencyList.containsKey(fromVertex)) {
            adjacencyList.get(fromVertex).remove(toVertex);
        }
    }

    /**
     * Получение список вершин-соседей данной вершины.
     *
     * @param vertex имя вершины
     * @return список соседей
     */
    @Override
    public List<String> getNeighbors(String vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    /**
     * Получение графа из файла.
     *
     * @param fileName имя файла
     * @throws IOException ошибка чтения
     */
    @Override
    public void readFromFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    addVertex(parts[0]);
                    addVertex(parts[1]);
                    addEdge(parts[0], parts[1]);
                }
            }
        }
    }

    /**
     * Получение списка всех вершин.
     *
     * @return список всех вершин
     */
    @Override
    public List<String> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Равенство графов.
     *
     * @param obj граф, с которым необходимо провести сравнение.
     * @return True/False
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdjacencyListGraph that = (AdjacencyListGraph) obj;
        return Objects.equals(adjacencyList, that.adjacencyList);
    }

    /**
     * Строковое представление графа.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(": ");
            for (String neighbor : adjacencyList.get(vertex)) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
