package edu.wpi.teamg.ORMClasses;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import org.controlsfx.control.SearchableComboBox;

public class Astar implements Algorithm {
  DAORepo dao = new DAORepo();

  @Override
  public ArrayList<String> process(SearchableComboBox startLocDrop, SearchableComboBox endLocDrop)
      throws SQLException {
    ArrayList<String> path;

    NodeDAO nodeDAO = new NodeDAO();
    EdgeDAO edgeDAO = new EdgeDAO();
    ArrayList<Node> allNodes = new ArrayList<>(nodeDAO.getAll().values());
    ArrayList<Edge> allEdges = new ArrayList<>(edgeDAO.getAll().values());

    String L1StartNodeLongName = (String) startLocDrop.getValue();
    String L1EndNodeLongName = (String) endLocDrop.getValue();

    int L1StartNodeID = dao.getNodeIDbyLongName(L1StartNodeLongName);
    int L1EndNodeID = dao.getNodeIDbyLongName(L1EndNodeLongName);

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
    path = G1.aStarAlg(Adj, startNode, endNode);

    return path;
  }
}
