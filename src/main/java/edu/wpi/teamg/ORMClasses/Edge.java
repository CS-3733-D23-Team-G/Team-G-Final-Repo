package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Edge {

  @Getter @Setter private int startNode;
  @Getter @Setter private int endNode;
  @Getter @Setter private String edgeID;

  public Edge() {}

  public Edge(int startNode, int endNode, String edgeID) {
    this.startNode = startNode;
    this.endNode = endNode;
    this.edgeID = edgeID;
  }
}
