package ru.nsu.yakhimovich.graph;

import java.io.IOException;
import java.util.*;

/**
 * Интерфейс, представляющий параметризованный граф.
 *
 * @param <T> тип данных, используемый для представления вершин
 */
public interface Graph<T> {

    /**
     * Добавление вершины в граф.
     *
     * @param vertex вершина, которая будет добавлена
     */
    void addVertex(T vertex);

    /**
     * Удаление вершины из графа + рёбра связанные с этой вершиной.
     *
     * @param vertex вершина, которая будет удалена
     */
    void removeVertex(T vertex);

    /**
     * Добавление направленного ребра между двумя вершинами.
     *
     * @param fromVertex вершина, от которой исходит ребро
     * @param toVertex   вершина, к которой ведет ребро
     */
    void addEdge(T fromVertex, T toVertex);

    /**
     * Удаление направленного ребра между двумя вершинами.
     *
     * @param fromVertex вершина, от которой исходит ребро
     * @param toVertex   вершина, к которой ведет ребро
     */
    void removeEdge(T fromVertex, T toVertex);

    /**
     * Получение списка всех соседних вершин для заданной вершины.
     * Соседние вершины — это вершины, в которые можно попасть по рёбрам из заданной вершины.
     *
     * @param vertex вершина, соседи которой запрашиваются
     * @return список соседних вершин
     */
    List<T> getNeighbors(T vertex);

    /**
     * Чтение графа из файла с фиксированным форматом.
     *
     * @param fileName имя файла, из которого следует загрузить граф
     * @param parser   функциональный параметр, используемый для парсинга строкового представления вершины
     * @throws IOException если возникли ошибки при чтении файла
     */
    void readFromFile(String fileName, java.util.function.Function<String, T> parser) throws IOException;

    /**
     * Выполнение топологической сортировки графа. Топологическая сортировка возможна только для
     * ацикличных ориентированных графов.
     *
     * @return список вершин в порядке топологической сортировки
     */
    default List<T> topologicalSort() {
        List<T> sortedList = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Deque<T> stack = new ArrayDeque<>();

        for (T vertex : getVertices()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            sortedList.add(stack.pollLast());
        }

        return sortedList;
    }

    /**
     * Вспомогательный метод для рекурсивного выполнения топологической сортировки.
     *
     * @param vertex  текущая вершина, для которой выполняется сортировка
     * @param visited множество посещённых вершин
     * @param stack   стек, в который добавляются вершины после обработки
     */
    default void topologicalSortUtil(T vertex, Set<T> visited, Deque<T> stack) {
        visited.add(vertex);
        for (T neighbor : getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        stack.addLast(vertex);
    }

    /**
     * Получение списка всех вершин графа.
     *
     * @return список всех вершин в графе
     */
    List<T> getVertices();
}
