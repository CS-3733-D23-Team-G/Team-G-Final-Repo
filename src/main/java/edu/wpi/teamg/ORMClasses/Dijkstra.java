package edu.wpi.teamg.ORMClasses;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Dijkstra implements Algorithm {
  DAORepo dao = new DAORepo();

  @Override
  public ArrayList<String> process(
      MFXFilterComboBox startLocDrop, MFXFilterComboBox endLocDrop, ArrayList<Move> movin)
      throws SQLException {
    ArrayList<String> path;

    NodeDAO nodeDAO = new NodeDAO();
    EdgeDAO edgeDAO = new EdgeDAO();

    // This is where accounting for moveStarts
    // MoveDAO moveDAO = new MoveDAO();

    ArrayList<Node> allNodes = new ArrayList<>(nodeDAO.getAll().values());
    ArrayList<Edge> allEdges = new ArrayList<>(edgeDAO.getAll().values());

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

    Node[] nodeArray = new Node[allNodes.size()];
    for (int i = 0; i < allNodes.size(); i++) {
      nodeArray[i] = allNodes.get(i);
    }
    Edge[] edgeArray = new Edge[allEdges.size()];
    for (int i = 0; i < allEdges.size(); i++) {
      edgeArray[i] = allEdges.get(i);
    }

    int startNode = 0;
    int endNode = 0;
    for (int i = 0; i < allNodes.size(); i++) {

      if (nodeArray[i].getNodeID() == L1StartNodeID) {
        startNode = i;
      }
      if (nodeArray[i].getNodeID() == L1EndNodeID) {
        endNode = i;
      }
    }

    Graph G1 = new Graph(nodeArray, edgeArray);
    int[][] Adj = G1.createWeightedAdj();

    System.out.println(nodeArray[0].getNodeID());
    path = G1.Dijkstra(Adj, startNode, endNode);

    return path;
  }
}
