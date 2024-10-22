package ru.nsu.yakhimovich.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Реализация графа через матрицу инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private final List<String> vertices;  // Список вершин
    private final List<String[]> edges;   // Список рёбер в виде двух массивов [fromVertex, toVertex]
    private int[][] incidenceMatrix; // Матрица инцидентности
    private int edgeCount; // Количество рёбер

    /**
     * Инициализация объекта с указанным размером.
     *
     * @param initialVertexCapacity количество вершин
     * @param initialEdgeCapacity   количество ребер
     */
    public IncidenceMatrixGraph(int initialVertexCapacity, int initialEdgeCapacity) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incidenceMatrix = new int[initialVertexCapacity][initialEdgeCapacity];
        edgeCount = 0;
    }

    /**
     * Добавление вершины.
     *
     * @param vertex имя вершины
     */
    @Override
    public void addVertex(String vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            if (vertices.size() > incidenceMatrix.length) {
                resizeVertexMatrix();
            }
        }
    }

    /**
     * Удаление вершины.
     *
     * @param vertex имя вершины
     */
    @Override
    public void removeVertex(String vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex != -1) {
            vertices.remove(vertexIndex);
            for (int i = 0; i < edgeCount; i++) {
                incidenceMatrix[vertexIndex][i] = 0; // Удаление инцидентных ребер
            }
            for (int i = vertexIndex; i < vertices.size(); i++) {
                incidenceMatrix[i] = incidenceMatrix[i + 1]; // Сдвиг строк
            }
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
        if (vertices.contains(fromVertex) && vertices.contains(toVertex)) {
            edges.add(new String[]{fromVertex, toVertex});
            if (edgeCount >= incidenceMatrix[0].length) {
                resizeEdgeMatrix();
            }
            int fromIndex = vertices.indexOf(fromVertex);
            int toIndex = vertices.indexOf(toVertex);
            incidenceMatrix[fromIndex][edgeCount] = -1;  // для исходящей вершины
            incidenceMatrix[toIndex][edgeCount] = 1;    // для входящей вершины
            edgeCount++;
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
        for (int i = 0; i < edgeCount; i++) {
            if (edges.get(i)[0].equals(fromVertex) && edges.get(i)[1].equals(toVertex)) {
                edges.remove(i);
                for (int j = 0; j < vertices.size(); j++) {
                    incidenceMatrix[j][i] = 0; // Удаление инцидентности
                }
                for (int j = i; j < edgeCount - 1; j++) {
                    for (int k = 0; k < vertices.size(); k++) {
                        incidenceMatrix[k][j] = incidenceMatrix[k][j + 1]; // Сдвиг столбцов
                    }
                }
                edgeCount--;
                break;
            }
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
        List<String> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex != -1) {
            for (int i = 0; i < edgeCount; i++) {
                if (incidenceMatrix[vertexIndex][i] == -1) { // Исходящее ребро
                    for (int j = 0; j < vertices.size(); j++) {
                        if (incidenceMatrix[j][i] == 1) {
                            neighbors.add(vertices.get(j));
                        }
                    }
                }
            }
        }
        return neighbors;
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
        return new ArrayList<>(vertices);
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
        IncidenceMatrixGraph that = (IncidenceMatrixGraph) obj;
        return edgeCount == that.edgeCount && Objects.equals(vertices, that.vertices)
                && Arrays.deepEquals(incidenceMatrix, that.incidenceMatrix);
    }

    /**
     * Строковое представление графа.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String vertex : vertices) {
            sb.append(vertex).append(": ");
            List<String> neighbors = getNeighbors(vertex);
            for (String neighbor : neighbors) {
                sb.append(neighbor).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Увеличение размера матрицы вершин при переполнении.
     */
    private void resizeVertexMatrix() {
        int newSize = incidenceMatrix.length * 2;
        int[][] newMatrix = new int[newSize][incidenceMatrix[0].length];
        for (int i = 0; i < incidenceMatrix.length; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, incidenceMatrix[i].length);
        }
        incidenceMatrix = newMatrix;
    }

    /**
     * Увеличение размера матрицы ребер при переполнении.
     */
    private void resizeEdgeMatrix() {
        int newSize = incidenceMatrix[0].length * 2;
        for (int i = 0; i < incidenceMatrix.length; i++) {
            incidenceMatrix[i] = Arrays.copyOf(incidenceMatrix[i], newSize);
        }
    }
}
