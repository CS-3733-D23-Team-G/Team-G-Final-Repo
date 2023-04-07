package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Edge {

  @Getter @Setter private int startNode;
  @Getter @Setter private int endNode;
  @Getter @Setter private String edgeID;

  public Edge() {}

  public Edge(int startNode, int endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public int distance(Node A, Node B) {
    double x1 = A.getXcoord();
    double x2 = B.getXcoord();
    double y1 = A.getYcoord();
    double y2 = B.getYcoord();
    int distance = (int) Math.sqrt((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    return distance;
  }
}
