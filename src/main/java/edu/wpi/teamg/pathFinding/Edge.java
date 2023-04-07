package edu.wpi.teamg.pathFinding;

public class Edge {
  private String edgeID;
  private Node startNode;
  private Node endNode;

  public Edge(String ID, Node start, Node end) {
    edgeID = ID;
    startNode = start;
    endNode = end;
  }

  public int distance(Node A, Node B) {
    double x1 = A.getNodeX();
    double x2 = B.getNodeX();
    double y1 = A.getNodeY();
    double y2 = B.getNodeY();
    int distance = (int) Math.sqrt((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    return distance;
  }

  public Node getEndNode() {
    return endNode;
  }

  public Node getStartNode() {
    return startNode;
  }

  public String getEdgeID() {
    return edgeID;
  }
}
