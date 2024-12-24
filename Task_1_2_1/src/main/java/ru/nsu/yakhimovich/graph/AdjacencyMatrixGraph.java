package ru.nsu.yakhimovich.graph;

import java.util.*;

/**
 * Реализация графа через матрицу смежности.
 *
 * @param <T> тип данных, представляющий вершины графа
 */
public class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final Map<T, Integer> vertexIndexMap;
    private final List<T> vertices;
    private boolean[][] adjacencyMatrix;

    /**
     * Инициализация объекта с указанным размером.
     *
     * @param initialCapacity размер матрицы
     */
    public AdjacencyMatrixGraph(int initialCapacity) {
        vertexIndexMap = new HashMap<>();
        vertices = new ArrayList<>();
        adjacencyMatrix = new boolean[initialCapacity][initialCapacity];
    }

    /**
     * Добавление вершины.
     *
     * @param vertex вершина
     */
    @Override
    public void addVertex(T vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertices.size());
            vertices.add(vertex);
            if (vertices.size() > adjacencyMatrix.length) {
                resizeMatrix();
            }
        }
    }

    /**
     * Удаление вершины.
     *
     * @param vertex вершина
     */
    @Override
    public void removeVertex(T vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.remove(vertex);
            vertices.remove(vertex);
            for (int i = 0; i < vertices.size(); i++) {
                adjacencyMatrix[i][index] = false;
                adjacencyMatrix[index][i] = false;
            }
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
        if (vertexIndexMap.containsKey(fromVertex) && vertexIndexMap.containsKey(toVertex)) {
            int fromIndex = vertexIndexMap.get(fromVertex);
            int toIndex = vertexIndexMap.get(toVertex);
            adjacencyMatrix[fromIndex][toIndex] = true;
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
        if (vertexIndexMap.containsKey(fromVertex) && vertexIndexMap.containsKey(toVertex)) {
            int fromIndex = vertexIndexMap.get(fromVertex);
            int toIndex = vertexIndexMap.get(toVertex);
            adjacencyMatrix[fromIndex][toIndex] = false;
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
        List<T> neighbors = new ArrayList<>();
        if (vertexIndexMap.containsKey(vertex)) {
            int index = vertexIndexMap.get(vertex);
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[index][i]) {
                    neighbors.add(vertices.get(i));
                }
            }
        }
        return neighbors;
    }

    /**
     * Получение списка всех вершин.
     *
     * @return список всех вершин
     */
    @Override
    public List<T> getVertices() {
        return new ArrayList<>(vertices);
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
        AdjacencyMatrixGraph<?> that = (AdjacencyMatrixGraph<?>) obj;
        return Objects.equals(vertices, that.vertices)
                && Arrays.deepEquals(adjacencyMatrix, that.adjacencyMatrix);
    }

    /**
     * Строковое представление графа.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertices.size(); i++) {
            sb.append(vertices.get(i)).append(": ");
            for (int j = 0; j < vertices.size(); j++) {
                if (adjacencyMatrix[i][j]) {
                    sb.append(vertices.get(j)).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Увеличение размера матрицы при переполнении.
     */
    private void resizeMatrix() {
        int newSize = adjacencyMatrix.length * 2;
        boolean[][] newMatrix = new boolean[newSize][newSize];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
        }
        adjacencyMatrix = newMatrix;
    }
}
