package edu.wpi.teamg.ORMClasses;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.controlsfx.control.SearchableComboBox;

public class BFS implements Algorithm {
  DAORepo dao = new DAORepo();

  @Override
  public ArrayList<String> process(
      SearchableComboBox startLocDrop, SearchableComboBox endLocDrop, ArrayList<Move> movin)
      throws SQLException {

    ArrayList<String> path;

    NodeDAO nodeDao = new NodeDAO();
    EdgeDAO edgeDAO = new EdgeDAO();

    // This is where accounting for moveStarts
    // MoveDAO moveDAO = new MoveDAO();

    HashMap<Integer, Node> nodeMap = nodeDao.getAll();
    HashMap<String, Edge> edgeMap = edgeDAO.getAll();

    ArrayList<Node> nodeList = new ArrayList<>(nodeMap.values());
    ArrayList<Edge> edgeList = new ArrayList<>(edgeMap.values());

    //    for (int i = 0; i < nodeList.size(); i++) {
    //      System.out.println(nodeList.get(i).getNodeID());
    //    }
    //
    //    for (int i = 0; i < edgeList.size(); i++) {
    //      System.out.println(edgeList.get(i).getEdgeID());
    //    }
    String L1StartNodeLongName = (String) startLocDrop.getValue();
    String L1EndNodeLongName = (String) endLocDrop.getValue();

    int L1StartNodeID = 0;
    int L1EndNodeID = 0;
    for (int i = 0; i < movin.size(); i++) {
      if (Objects.equals(movin.get(i).getLongName(), startLocDrop.getValue().toString())) {
        L1StartNodeID = movin.get(i).getNodeID();
      }
      if (Objects.equals(movin.get(i).getLongName(), endLocDrop.getValue().toString())) {
        L1EndNodeID = movin.get(i).getNodeID();
      }
    }

    Node[] nodeArray = new Node[nodeList.size()];
    Edge[] edgeArray = new Edge[edgeList.size()];

    for (int i = 0; i < nodeList.size(); i++) {
      nodeArray[i] = nodeList.get(i);
    }
    for (int i = 0; i < edgeList.size(); i++) {
      edgeArray[i] = edgeList.get(i);
    }

    int startNode = 0;
    int endNode = 0;
    for (int i = 0; i < nodeList.size(); i++) {

      if (nodeArray[i].getNodeID() == L1StartNodeID) {
        startNode = i;
      }
      if (nodeArray[i].getNodeID() == L1EndNodeID) {
        endNode = i;
      }
    }

    System.out.println(startNode);
    System.out.println(endNode);

    Graph graph = new Graph(nodeArray, edgeArray);
    path = graph.breadthFirstSearch(graph.createWeightedAdj(), startNode, endNode);
    // setPath(path);

    System.out.println("Start node:" + L1StartNodeID);
    System.out.println("End node:" + L1EndNodeID);

    //        for (String s : path) {
    //          System.out.println(s);
    //        }

    ArrayList<String> finalPath = new ArrayList<>();
    for (int i = 0; i < path.size(); i++) {
      finalPath.add(String.valueOf(nodeArray[Integer.parseInt(path.get(i))].getNodeID()));
    }

    return finalPath;
  }
}
