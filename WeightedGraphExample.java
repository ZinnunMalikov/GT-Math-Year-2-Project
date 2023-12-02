import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class WeightedGraph {
    private Map<Integer, List<Edge>> adjacencyList;

    public WeightedGraph() {
        this.adjacencyList = new HashMap<>();
    }
    
    private static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    
    public void addVertex(int vertex) {
        adjacencyList.put(vertex, new ArrayList<>());
    }

    // add a weighted edge between two vertices
    public void addEdge(int source, int destination, int weight) {
        // ensures vertices exist
        if (!adjacencyList.containsKey(source)) {
            addVertex(source);
        }
        if (!adjacencyList.containsKey(destination)) {
            addVertex(destination);
        }

        // add the weighted edge
        adjacencyList.get(source).add(new Edge(destination, weight));
    }
    
    public void changeEdgeWeight(int source, int destination, int newWeight) {
        List<Edge> edges = adjacencyList.get(source);

        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.destination == destination) {
                    edge.weight = newWeight;
                    return; // assumes one edge b/w two vertices
                }
            }
        }

        // if edge isnt found
        System.out.println("Edge not found: (" + source + ", " + destination + ")");
    }

    // Dijkstra's Algorithm
    public Map<Integer, Integer> dijkstra(int startVertex) {
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));

        // initialize distances
        for (int vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(startVertex, 0);

        // initialize the priority queue with the start vertex
        minHeap.add(new Edge(startVertex, 0));

        while (!minHeap.isEmpty()) {
            Edge current = minHeap.poll();

            for (Edge neighbor : adjacencyList.get(current.destination)) {
                int newDistance = distances.get(current.destination) + neighbor.weight;

                if (newDistance < distances.get(neighbor.destination)) {
                    distances.put(neighbor.destination, newDistance);
                    minHeap.add(new Edge(neighbor.destination, newDistance));
                }
            }
        }

        return distances;
    }

    // print the graph with weights
    public void printGraph() {
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Edge edge : entry.getValue()) {
                System.out.print("(" + edge.destination + ", " + edge.weight + ") ");
            }
            System.out.println();
        }
    }
    public int numVert() {
        return adjacencyList.size();
    }
    public boolean hasEdge(int source, int destination) {
        List<Edge> edges = adjacencyList.get(source);

        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.destination == destination) {
                    return true;
                }
            }
        }

        return false;
    }
    public int getWeight(int source, int destination) {
        List<Edge> edges = adjacencyList.get(source);

        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.destination == destination) {
                    return edge.weight;
                }
            }
        }
        
        return -1;
    // needed some return value
        
    }
    
    
}

public class WeightedGraphExample {
    public static void main(String[] args) {
        // new weighted graph
        WeightedGraph weightedGraph = new WeightedGraph();
        
        WeightedGraph copyGraph = new WeightedGraph();
        
        weightedGraph.addVertex(1);
        weightedGraph.addVertex(2);
        weightedGraph.addVertex(3);
        weightedGraph.addVertex(4);
        weightedGraph.addVertex(5);

        weightedGraph.addEdge(1, 2, 3);
        weightedGraph.addEdge(1, 3, 5);
        weightedGraph.addEdge(2, 3, 2);
        weightedGraph.addEdge(3, 4, 7);
        weightedGraph.addEdge(4, 1, 10);
        weightedGraph.addEdge(2, 4, 2);
        weightedGraph.addEdge(1, 5, 20);
        weightedGraph.addEdge(2, 5, 10);

        System.out.println("Vertices and their edges");
        weightedGraph.printGraph();
        System.out.println();
        System.out.println("Distances from vertex 1: " + weightedGraph.dijkstra(1));
        System.out.println();
        copyGraph.addVertex(1);
        copyGraph.addVertex(2);
        copyGraph.addVertex(3);
        copyGraph.addVertex(4);
        copyGraph.addVertex(5);

        copyGraph.addEdge(1, 2, 3);
        copyGraph.addEdge(1, 3, 5);
        copyGraph.addEdge(2, 3, 2);
        copyGraph.addEdge(3, 4, 7);
        copyGraph.addEdge(4, 1, 10);
        copyGraph.addEdge(2, 4, 2);
        copyGraph.addEdge(1, 5, 20);
        copyGraph.addEdge(2, 5, 10);
        
        
        System.out.println();
        //System.out.println("Distances from vertex 1: " + copyGraph.dijkstra(1));
       // System.out.println();
        
        for (int i = 1; i <= weightedGraph.numVert(); i++)
        {
            for (int j = 1; j <= weightedGraph.numVert(); j++)
            {
                if (i == j)
                {
                    continue;
                }
                if (weightedGraph.hasEdge(i, j))
                {
                    int randWeight = (int) (Math.random() * 5);
                    weightedGraph.changeEdgeWeight(i, j, copyGraph.getWeight(i, j) + randWeight);
                    continue;
                }
                else
                {
                    continue;
                }
            }
        }
        
       System.out.println("New weighted graph after randomization of weights");
       weightedGraph.printGraph();

        // apply algorithm from vertex 1
        System.out.println();
        System.out.println("New Optimization");
        System.out.println("Distances from vertex 1: " + weightedGraph.dijkstra(1));
        
        
    }
}
