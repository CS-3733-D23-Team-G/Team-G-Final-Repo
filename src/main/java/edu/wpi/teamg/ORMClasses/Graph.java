package edu.wpi.teamg.ORMClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
  private edu.wpi.teamg.ORMClasses.Node[] V;
  private edu.wpi.teamg.ORMClasses.Edge[] E;

  public Graph(edu.wpi.teamg.ORMClasses.Node[] N, Edge[] ed) {
    V = N;
    E = ed;
  }

  public int[][] createWeightedAdj() {
    int[][] A1 = new int[V.length][V.length];
    // populate the adj matrix with zeros or inf (change as needed)
    for (int i = 0; i < V.length; i++) {
      for (int j = 0; j < V.length; j++) {
        A1[i][j] = 0;
      }
    }
    // this will connect the nodes to edges
    for (int i = 0; i < E.length; i++) { // i is number of rows
      // we will get the first edge out of the Edge[] array and
      // get the start and end nodes from it.
      int start = E[i].getStartNode();
      int end = E[i].getEndNode();
      int vertice_numS = 0;
      int vertice_numE = 0;
      // then we will find where the nodes are in the Node[] array and save the int value
      for (int j = 0; j < V.length; j++) {
        if (V[j].getNodeID() == start) {
          vertice_numS = j;
        }
        if (V[j].getNodeID() == end) {
          vertice_numE = j;
        }
      }
      // we then calulate the distance between the two nodes and put it in the A1[][] array
      // since the adj matrix is sysmetric the number in the row x colum and colum x row will be the
      // same.
      A1[vertice_numS][vertice_numE] = E[i].distance(V[vertice_numS], V[vertice_numE]);

      A1[vertice_numE][vertice_numS] = E[i].distance(V[vertice_numE], V[vertice_numS]);
    }
    // System.out.println("A1 " + E[i].distance(V[vertice_numS], V[vertice_numE]));
    // System.out.println("A2 " + E[i].distance(V[vertice_numS], V[vertice_numE]));
    return A1;
  }

  public ArrayList<String> aStarAlg(int[][] aMatrix, int start, int end) {
    // Number of vertices in the example. For us, it will be 48
    int vertex = V.length;
    // Sets up our distances, so we can process our nodes into our dis array with "inf" values
    int totalDistance = 0;
    // If the vertex has been visited
    ArrayList<Boolean> verVisited = new ArrayList<>();
    // Here we have a list of the minimum path to each vertex
    ArrayList<Integer> dis = new ArrayList<>();
    // Here we will push parent nodes into an array, so we can get the path itself
    int[] parent = new int[vertex];
    // MAXES OUT EVERY NODE BUT THE STARTING NODE
    // So think of our table from the video If we start from node 0
    // (0, inf, inf, inf, inf, inf, inf, inf)
    for (int v = 0; v < vertex; v++) {
      totalDistance = Integer.MAX_VALUE; // Java version of infinity is MAX_VALUE
      parent[v] = Integer.MAX_VALUE;
      // When we are at our beginning node we set it to 0
      // So, we know where to start
      if (v == start) {
        totalDistance = 0;
      }
      // Here we haven't visited any nodes yet, so we are marking all the nodes false
      verVisited.add(false);
      // Adds our inf to the table, so we are left with our
      // (0, inf, inf, inf, inf, inf, inf, inf)
      dis.add(totalDistance);
    }
    // Stores a -1 in the beginning of the parent array
    // This will be used to indicate the end of our list of nodes later
    parent[start] = -1;
    // This is the actual computation portion
    // Starting for every vertex (not sure why Math.abs is here we can probs delete)
    for (int i = 0; i < Math.abs(vertex) - 1; i++) {
      // uStarV = next nearest vertex
      int uStarV = smallestDistance(dis, verVisited);
      // uStarD = is the distance to get to our current node
      int uStarD = dis.get(uStarV);
      // Mark we visited our nearest node
      verVisited.set(uStarV, true);
      // CHECKS TO SEE IF (THE CURRENT NODE WE ARE AT + A CONNECTED WEIGHTED EDGE) IS LESS THAN A
      // PATH WE ALREADY HAVE
      // For every thing in the row of the vertex
      for (int j = 0; j < vertex; j++) {
        // If we have a connection
        if (aMatrix[uStarV][j] != 0) {
          // if the total distance to our node + the added distance is less than the distance in our
          // chart
          if (uStarD + aMatrix[uStarV][j] < dis.get(j)) {
            // Add to Parent which will be our list of nodes
            parent[j] = uStarV;
            // replace the distance in list to our new minimum
            dis.set(j, uStarD + aMatrix[uStarV][j]);
          }
        }
      }
    }

    ArrayList<String> solution = new ArrayList<>();
    printMySolution(start, parent, end, solution);

    return solution;
  }

  // HELPER FUNCTION FOR OUR A* alg
  public int smallestDistance(ArrayList<Integer> distance, ArrayList<Boolean> vert) {
    int minVal = 10000000;
    int valVert = 0;
    for (int i = 0; i < distance.size(); i++) {
      // checks if we have been to the node and that it is connected by and edge so we can go to it.
      if (!vert.get(i) & distance.get(i) < minVal) {
        minVal = distance.get(i);
        valVert = i;
      }
    }

    return valVert;
  }

  // HELPER FUNCTION FOR OUR A* alg
  public void printMySolution(int start, int[] parentNodes, int end, ArrayList<String> s) {
    System.out.println(" ");
    System.out.println("The Nodes that will lead to your total min path are: ");
    if (end != start) {
      printPath(end, parentNodes, start, s);
    }
  }

  // HELPER FUNCTION FOR OUR A* alg
  public void printPath(int current, int[] parent, int start, ArrayList<String> s) {
    if (current == -1) {
      return;
    }
    if (current == Integer.MAX_VALUE) {
      System.out.println("Error No Path Found");
      return;
    }
    printPath(parent[current], parent, start, s);
    // System.out.print(V[current].getNodeID() + " ");
    s.add(Integer.toString(V[current].getNodeID()));
  }

  public static ArrayList<String> depthFirstSearch(
      int[][] adjacencyMatrix, int startNode, int endNode) {
    // Create a boolean array to keep track of visited nodes
    boolean[] visited = new boolean[adjacencyMatrix.length];
    // Create an empty ArrayList to store the path
    ArrayList<String> path = new ArrayList<>();
    // Call the recursive helper function
    depthFirstSearchHelper(adjacencyMatrix, startNode, endNode, visited, path);
    return path;
  }

  private static boolean depthFirstSearchHelper(
      int[][] adjacencyMatrix,
      int currentNode,
      int endNode,
      boolean[] visited,
      ArrayList<String> path) {
    // Mark the current node as visited and add it to the path
    visited[currentNode] = true;
    path.add(Integer.toString(currentNode));
    // Check if the current node is the end node
    if (currentNode == endNode) {
      return true;
    }
    // Iterate through all the adjacent nodes
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      // Check if there is an edge between the current node and the adjacent node
      if (adjacencyMatrix[currentNode][i] == 1) {
        // Check if the adjacent node has not been visited yet
        if (!visited[i]) {
          // Call the helper function recursively with the adjacent node as the new current node
          if (depthFirstSearchHelper(adjacencyMatrix, i, endNode, visited, path)) {
            return true;
          }
        }
      }
    }
    // If the end node is not found, remove the current node from the path and return false
    path.remove(path.size() - 1);
    return false;
  }

  public static ArrayList<String> breadthFirstSearch(
      int[][] adjacencyMatrix, int startNode, int endNode) {
    // Create a boolean array to keep track of visited nodes
    boolean[] visited = new boolean[adjacencyMatrix.length];
    // Create a queue for BFS
    Queue<Integer> queue = new LinkedList<>();
    // Create an empty ArrayList to store the path
    ArrayList<String> path = new ArrayList<>();
    // Mark the start node as visited and enqueue it
    visited[startNode] = true;
    queue.add(startNode);
    // Run BFS until the queue is empty
    while (!queue.isEmpty()) {
      // Dequeue a node from the queue and add it to the path
      int currentNode = queue.poll();
      path.add(Integer.toString(currentNode));
      // Check if the current node is the end node
      if (currentNode == endNode) {
        return path;
      }
      // Iterate through all the adjacent nodes
      for (int i = 0; i < adjacencyMatrix.length; i++) {
        // Check if there is an edge between the current node and the adjacent node
        if (adjacencyMatrix[currentNode][i] == 1) {
          // Check if the adjacent node has not been visited yet
          if (!visited[i]) {
            // Mark the adjacent node as visited and enqueue it
            visited[i] = true;
            queue.add(i);
          }
        }
      }
    }
    // If the end node is not found, return an empty path
    return new ArrayList<>();
  }

  public static void main(String[] args) {
    // Create an adjacency matrix for a simple graph
    int[][] adjacencyMatrix = {
      {0, 1, 1, 0},
      {1, 0, 1, 0},
      {1, 1, 0, 1},
      {0, 0, 1, 0}
    };
    // Test the depthFirstSearch method with different start and end nodes
    ArrayList<String> path = depthFirstSearch(adjacencyMatrix, 0, 3);
    System.out.println("Path from node 0 to node 3: " + path);
    path = depthFirstSearch(adjacencyMatrix, 1, 2);
    System.out.println("Path from node 1 to node 2: " + path);
    path = depthFirstSearch(adjacencyMatrix, 2, 0);
    System.out.println("Path from node 2 to node 0: " + path);
  }
}
