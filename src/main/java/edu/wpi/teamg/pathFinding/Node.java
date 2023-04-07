package edu.wpi.teamg.pathFinding;

public class Node {

  private String NodeID;
  private int nodeX;
  private int nodeY;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;;

  public Node(
      String NodeID,
      int nodeX,
      int nodeY,
      String floor,
      String nodeType,
      String building,
      String longName,
      String shortName) {
    this.NodeID = NodeID;
    this.nodeX = nodeX;
    this.nodeY = nodeY;
    this.floor = floor;
    this.nodeType = nodeType;
    this.building = building;
    this.longName = longName;
    this.shortName = shortName;
  }

  public Node(String NodeID, int nodeX, int nodeY, String floor, String building) {
    this.NodeID = NodeID;
    this.nodeX = nodeX;
    this.nodeY = nodeY;
    this.floor = floor;
    this.building = building;
  }

  public int getNodeX() {
    return nodeX;
  }

  public int getNodeY() {
    return nodeY;
  }

  public String getNodeID() {
    return NodeID;
  }

  public String getFloor() {
    return floor;
  }
}
