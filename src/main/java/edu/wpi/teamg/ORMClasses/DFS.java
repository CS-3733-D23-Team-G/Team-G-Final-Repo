package edu.wpi.teamg.ORMClasses;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.controlsfx.control.SearchableComboBox;

public class DFS implements Algorithm {
  DAORepo dao = new DAORepo();

  @Override
  public ArrayList<String> process(SearchableComboBox startLocDrop, SearchableComboBox endLocDrop)
      throws SQLException {

    ArrayList<String> path = new ArrayList<>();

    NodeDAO nodeDao = new NodeDAO();
    EdgeDAO edgeDAO = new EdgeDAO();

    HashMap<Integer, Node> nodeMap = nodeDao.getAll();
    HashMap<String, Edge> edgeMap = edgeDAO.getAll();

    ArrayList<Node> nodeList = new ArrayList<>(nodeMap.values());
    ArrayList<Edge> edgeList = new ArrayList<>(edgeMap.values());

    String L1StartNodeLongName = (String) startLocDrop.getValue();
    String L1EndNodeLongName = (String) endLocDrop.getValue();

    int L1StartNodeID = dao.getNodeIDbyLongName(L1StartNodeLongName);
    int L1EndNodeID = dao.getNodeIDbyLongName(L1EndNodeLongName);

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

    Graph graph = new Graph(nodeArray, edgeArray);
    path = graph.depthFirstSearch(graph.createWeightedAdj(), startNode, endNode);
    //  setPath(path);

    System.out.println("Start node:" + L1StartNodeID);
    System.out.println("End node:" + L1EndNodeID);

    ArrayList<String> finalPath = new ArrayList<>();
    for (int i = 0; i < path.size(); i++) {
      finalPath.add(String.valueOf(nodeArray[Integer.parseInt(path.get(i))].getNodeID()));
    }

    return finalPath;
  }
}
