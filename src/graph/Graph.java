package graph;

import java.util.Arrays;

import datastructures.LinkedList; // Our LinkedList Implementation

public class Graph {
    private final LinkedList<LinkedList<Integer>> adj_list = new LinkedList<>();
    private final LinkedList<LinkedList<Integer>> inv_adj_list = new LinkedList<>();

    public int vertexCount() {
        return adj_list.getSize();
    }

    public int[] getNeighbors(int vertex) {
        int[] edges = new int[adj_list.get(vertex).getSize()];

        for(int i = 0; i < edges.length; i++) {
            edges[i] = adj_list.get(vertex).get(i);
        }

        return edges;
    }

    private int[] getInvertedNeighbors(int vertex) {
        int[] edges = new int[inv_adj_list.get(vertex).getSize()];

        for(int i = 0; i < edges.length; i++) {
            edges[i] = inv_adj_list.get(vertex).get(i);
        }

        return edges;
    }

    public void addVertex() {
        adj_list.add(new LinkedList<>());
        inv_adj_list.add(new LinkedList<>());
    }

    public void addDirectedEdge(int src, int dest) {
        if (!adj_list.get(src).contains(dest)){
            adj_list.get(src).add(dest);
            inv_adj_list.get(dest).add(src);
        }
    }

    public void addEdge(int src, int dest) {
        addDirectedEdge(src, dest);
        addDirectedEdge(dest, src);
    }

    public void removeDirectedEdge(int src, int dest) {
        if (adj_list.get(src).contains(dest)) {
            adj_list.get(src).deleteNode(dest);
            inv_adj_list.get(dest).deleteNode(src);
        }
    }

    public void removeEdge(int src, int dest) {
        removeDirectedEdge(src, dest);
        removeDirectedEdge(dest, src);
    }

    public void removeAllEdges(int vertex) {
        // Edges from vertex
        for (int i = 0; i < adj_list.get(vertex).getSize(); i++) {
            int dest = adj_list.get(vertex).get(i);
            removeDirectedEdge(vertex, dest);
        }

        // Edges to vertex
        for (int i = 0; i < inv_adj_list.get(vertex).getSize(); i++) {
            int dest = inv_adj_list.get(vertex).get(i);
            removeDirectedEdge(dest, vertex);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public int[] BFS(int start, int end) {
        // Initialize array of node distances
        int[] visited = new int[vertexCount()];
        Arrays.fill(visited, -1);

        LinkedList<Integer> node_queue = new LinkedList<>();

        // BFS Traversal and Cost Factor
        int dist = 0; // Cost Factor
        visited[end] = dist;
        int current_node = end;

        A: while (true) {
            dist = visited[current_node];

            for (int node : getInvertedNeighbors(current_node)) {  // Working from end to start
                if (visited[node] == -1) { // If true, node was not visited yet
                    node_queue.add(node);
                    visited[node] = dist + 1;

                    if (node == start) {
                        break A;
                    }
                }
            }

            if (node_queue.getSize() == 0) { // If the node queue is empty, there is no path
                return new int[]{}; // Return empty array
            }

            // Update current node
            current_node = node_queue.get(0);
            node_queue.delete(0);
        }

        current_node = start;
        dist = visited[start];
        int[] path = new int[dist + 1];

        for(; dist > 0; dist--) {
            path[visited[start] - dist] = current_node;
            for (int node : getNeighbors(current_node)) {
                if (visited[node] == dist-1) {
                    current_node = node;
                    break;
                }
            }
        }

        path[visited[start]] = end;
        return path;
    }
    
    public static void main(String[] args) {
        // Initialize Graph
        Graph graph = new Graph();

        // Add 6 vertices
        for(int i = 0; i < 6; i++) {
            graph.addVertex();
        }

        // Add edges between vertices
        graph.addDirectedEdge(0, 4);
        graph.addDirectedEdge(0, 3);
        graph.addDirectedEdge(1, 4);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 0);
        graph.addDirectedEdge(2, 1);
        graph.addDirectedEdge(2, 3);
        graph.addDirectedEdge(3, 4);
        graph.addDirectedEdge(3, 5);
        graph.addDirectedEdge(4, 5);
        graph.addDirectedEdge(5, 0);

        // Run BFS
        System.out.println(Arrays.toString(graph.BFS(2, 0)));

        // Remove Node 1 and run again
        graph.removeAllEdges(1);
        System.out.println(Arrays.toString(graph.BFS(2, 0)));
    }
}
