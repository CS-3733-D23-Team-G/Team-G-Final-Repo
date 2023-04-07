package edu.wpi.teamg.pathFinding;

import java.util.ArrayList;

public class Graph {
  private Node[] V;
  private Edge[] E;

  public Graph(Node[] N, Edge[] ed) {
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
      // we will get the frist edge out of the Edge[] array and
      // get the start and end nodes from it.
      Node start = E[i].getStartNode();
      Node end = E[i].getEndNode();
      int vertice_numS = 0;
      int vertice_numE = 0;
      // then we will find where the nodes are in the Node[] array and save the int value
      for (int j = 0; j < V.length; j++) {
        if ((Integer.parseInt(V[j].getNodeID())) == (Integer.parseInt(start.getNodeID()))) {
          vertice_numS = j;
        }
        if ((Integer.parseInt(V[j].getNodeID())) == (Integer.parseInt(end.getNodeID()))) {
          vertice_numE = j;
        }
      }
      // we then calulate the distance between the two nodes and put it in the A1[][] array
      // since the adj matrix is sysmetric the number in the row x colum and colum x row will be the
      // same.
      A1[vertice_numS][vertice_numE] = E[i].distance(start, end);
      A1[vertice_numE][vertice_numS] = E[i].distance(end, start);
    }
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
    printPath(parent[current], parent, start, s);
    // System.out.print(V[current].getNodeID() + " ");
    s.add(V[current].getNodeID());
  }
}
