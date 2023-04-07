package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Edge {

  @Getter @Setter private int startNode;
  @Getter @Setter private int endNode;
  @Getter @Setter private String edgeID;

  public Edge() {}
}
