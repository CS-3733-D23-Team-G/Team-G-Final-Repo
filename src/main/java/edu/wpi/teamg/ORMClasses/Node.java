package edu.wpi.teamg.ORMClasses;

import lombok.Getter;
import lombok.Setter;

public class Node extends LocationName {

  @Getter @Setter private int nodeID;
  @Getter @Setter private int xcoord;
  @Getter @Setter private int ycoord;
  @Getter @Setter private String floor;
  @Getter @Setter private String building;

  public Node() {}
  public Node(int nodeID, int xcoord, int ycoord, String floor, String building){
    this.nodeID=nodeID;
    this.xcoord=xcoord;
    this.ycoord=ycoord;
    this.floor=floor;
    this.building=building;
  }


  public int getNodeX() {
    return xcoord;
  }

  public int getNodeY() {
    return ycoord;
  }

  public int getNodeID() {
    return nodeID;
  }

  public String getFloor() {
    return floor;
  }

}
