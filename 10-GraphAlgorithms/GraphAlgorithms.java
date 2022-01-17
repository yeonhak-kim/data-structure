import java.util.Queue;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
/**
 * Your implementation of various different graph algorithms.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903170274
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Either inputs are null.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjacents =
                graph.getAdjList(); //
        if (!adjacents.containsKey(start)) {
            throw new IllegalArgumentException("There is no start vertex.");
        }

        List<Vertex<T>> visitedVertices = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();

        visitedVertices.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {

            Vertex<T> currVertex = queue.remove();
            List<VertexDistance<T>> edges = adjacents.get(currVertex);

            for (int i = 0; i < edges.size(); i++) {
                if (!visitedVertices.contains(edges.get(i).getVertex())) {
                    queue.add(edges.get(i).getVertex());
                    visitedVertices.add(edges.get(i).getVertex());
                }
            }

        }
        return visitedVertices;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Either inputs are null.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjacents = graph.getAdjList();

        if (!adjacents.containsKey(start)) {
            throw new IllegalArgumentException("There is no start vertex.");
        }

        List<Vertex<T>> visitedVertices = new ArrayList<>();
        visitedVertices.add(start);
        dfsHelper(adjacents, visitedVertices, start);
        return visitedVertices;
    }

    /**
     * Helper method for dsf.
     * @param <T>   the generic typing of the data
     * @param adjacents set of adjacents
     * @param visitedVertices set of visited vertices
     * @param start start vertex
     */
    private static <T> void dfsHelper(
            Map<Vertex<T>, List<VertexDistance<T>>> adjacents,
            List<Vertex<T>> visitedVertices, Vertex<T> start) {

        for (VertexDistance<T> pair : adjacents.get(start)) {
            if (!visitedVertices.contains(pair.getVertex())) {
                visitedVertices.add(pair.getVertex());
                dfsHelper(adjacents, visitedVertices, pair.getVertex());
            }
        }

    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Either inputs are null.");
        }

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("There is no start vertex.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjacents = graph.getAdjList();

        Queue<VertexDistance<T>> priortyQ = new PriorityQueue<>();
        Map<Vertex<T>, Integer> returnMap = new HashMap<>();

        for (Vertex<T> vertex: adjacents.keySet()) {
            if (vertex.equals(start)) {
                returnMap.put(start, 0);
            } else {
                returnMap.put(vertex, Integer.MAX_VALUE);
            }
        }

        priortyQ.add(new VertexDistance<>(start, 0));

        while (!priortyQ.isEmpty()) {
            VertexDistance<T> currDistance = priortyQ.remove();
            for (VertexDistance<T> verD: adjacents.
                    get(currDistance.getVertex())) {
                if (returnMap.get(verD.getVertex())
                        > currDistance.getDistance() + verD.getDistance()) {

                    returnMap.put(verD.getVertex(),
                            currDistance.getDistance() + verD.getDistance());

                    priortyQ.add(new VertexDistance<>(verD.getVertex(),
                            currDistance.getDistance() + verD.getDistance()));

                }
            }
        }

        return returnMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input is null.");
        }

        DisjointSet<Vertex<T>> disjointSet
                = new DisjointSet<Vertex<T>>(graph.getVertices());
        PriorityQueue<Edge<T>> priorityQ = new PriorityQueue<Edge<T>>();
        Set<Edge<T>> mst = new HashSet<Edge<T>>();
        Set<Edge<T>> edges = graph.getEdges();

        // add all the edges to the Priority Queue
        // --> will be sorted in ascending weight order.
        for (Edge<T> edge : edges) {
            priorityQ.add(edge);
        }

        int counter = 0;
        while (!edges.isEmpty() && counter < graph.getVertices().size() - 1) {

            // priorityQ.poll() gets the head node
            // which has the minimum weight edge
            Edge<T> currEdge = priorityQ.poll();

            if (disjointSet.find(currEdge.getU())
                    != disjointSet.find(currEdge.getV())) {

                // add forward and backward edges
                mst.add(currEdge);
                mst.add(new Edge<T>(currEdge.getV(),
                        currEdge.getU(), currEdge.getWeight()));

                // update disjointSet
                disjointSet.union(currEdge.getU(), currEdge.getV());

                // (number of edges) = (number of vertices) - 1
                // --> termination condition
                counter = counter + 1;
            }
        }

        // Since forward and backward edges
        // been added, the condition for complete
        // mst will be mst.size() == 2(n-1)
        // where n represents the total number of vertices
        if (mst.size() == 2 * (graph.getVertices().size() - 1)) {
            return mst;
        }

        return null;
    }
}
