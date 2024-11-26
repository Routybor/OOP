package ru.nsu.yakhimovich.graph;

import java.util.*;

/**
 * Реализация графа через список смежности.
 *
 * @param <T> тип вершин в графе
 */
public class AdjacencyListGraph<T> implements Graph<T> {
    private final Map<T, List<T>> adjacencyList; // Список смежности

    /**
     * Инициализация объекта.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Добавление вершины.
     *
     * @param vertex вершина
     */
    @Override
    public void addVertex(T vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Удаление вершины.
     *
     * @param vertex вершина
     */
    @Override
    public void removeVertex(T vertex) {
        adjacencyList.remove(vertex);
        for (List<T> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }

    /**
     * Добавление ребра.
     *
     * @param fromVertex вершина, от которой направлено ребро
     * @param toVertex   вершина, к которой направлено ребро
     */
    @Override
    public void addEdge(T fromVertex, T toVertex) {
        if (adjacencyList.containsKey(fromVertex) && adjacencyList.containsKey(toVertex)) {
            adjacencyList.get(fromVertex).add(toVertex);
        }
    }

    /**
     * Удаление ребра.
     *
     * @param fromVertex вершина, от которой направлено ребро
     * @param toVertex   вершина, к которой направлено ребро
     */
    @Override
    public void removeEdge(T fromVertex, T toVertex) {
        if (adjacencyList.containsKey(fromVertex)) {
            adjacencyList.get(fromVertex).remove(toVertex);
        }
    }

    /**
     * Получение списка вершин-соседей данной вершины.
     *
     * @param vertex вершина
     * @return список соседей
     */
    @Override
    public List<T> getNeighbors(T vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    /**
     * Получение списка всех вершин.
     *
     * @return список всех вершин
     */
    @Override
    public List<T> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Равенство графов.
     *
     * @param obj граф, с которым необходимо провести сравнение
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
        AdjacencyListGraph<?> that = (AdjacencyListGraph<?>) obj;
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
        for (T vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(": ");
            for (T neighbor : adjacencyList.get(vertex)) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
