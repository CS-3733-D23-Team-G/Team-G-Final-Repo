/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.teamg;

// import edu.wpi.teamg.pathFinding.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.teamg.DAOs.*;
import edu.wpi.teamg.ORMClasses.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DefaultTest {

  @Test
  public void test() {}

  // Instantiates test
  DAORepo daoRepo = new DAORepo();
  NodeDAO nodeDAO = new NodeDAO();
  HashMap<Integer, Node> nodeHash = new HashMap<Integer, Node>();

  EdgeDAO edgeDAO = new EdgeDAO();
  HashMap<String, Edge> edgeHash = new HashMap<String, Edge>();

  MoveDAO moveDAO = new MoveDAO();
  List<Move> moveList = new ArrayList<Move>();

  LocationNameDAO nameDAO = new LocationNameDAO();
  HashMap<String, LocationName> locationNameHashMap = new HashMap<String, LocationName>();

  EmployeeDAO employeeDAO = new EmployeeDAO();
  HashMap<Integer, Employee> employeeHashMap = new HashMap<Integer, Employee>();

  AccountDAO accountDAO = new AccountDAO();
  HashMap<Integer, Account> accountHashMap = new HashMap<Integer, Account>();

  ConferenceRoomRequestDAO conferenceRoomRequestDAO = new ConferenceRoomRequestDAO();
  HashMap<Integer, ConferenceRoomRequest> conferenceRoomRequestHash =
      new HashMap<Integer, ConferenceRoomRequest>();

  FlowerRequestDAO flowerRequestDAO = new FlowerRequestDAO();
  HashMap<Integer, FlowerRequest> flowerRequestHash = new HashMap<Integer, FlowerRequest>();

  MealRequestDAO mealRequestDAO = new MealRequestDAO();
  HashMap<Integer, MealRequest> mealHash = new HashMap<Integer, MealRequest>();

  RequestDAO requestDAO = new RequestDAO();
  HashMap<Integer, Request> requestHash = new HashMap<Integer, Request>();

  // Test getAll() methods

  @Test
  public void testGetAllNodes() throws SQLException {
    nodeHash.put(1, new Node(1, 2265, 904, "L1", "45 Francis"));
    nodeHash.put(2, new Node(2, 2130, 849, "L2", "Tower"));
    nodeHash.put(3, new Node(3, 2360, 849, "L1", "45 Francis"));
    HashMap<Integer, Node> allNodes = nodeDAO.getAll();
    assertEquals(allNodes, nodeHash);
  }

  @Test
  public void testGetAllMoves() throws SQLException {
    Move move1 = new Move(1200, "Hall 3 Level 1", new Date(2023, 1, 1));
    Move move2 = new Move(1205, "	Hall 4 Level 2", new Date(2023, 1, 1));
    Move move3 = new Move(1210, "Hall 3 Level 3", new Date(2023, 1, 1));
    Move move4 = new Move(1215, "	Hall 1 Level L2", new Date(2023, 1, 1));

    moveList.add(move1);
    moveList.add(move2);
    moveList.add(move3);
    moveList.add(move4);

    List<Move> allMoves = moveDAO.getAll();
    assertEquals(allMoves, moveList);
  }

  @Test
  public void testGetAllConferenceRoomRequest() throws SQLException {

    conferenceRoomRequestHash.put(1, new ConferenceRoomRequest(1, new Time(12, 30, 0), "meeting"));
    conferenceRoomRequestHash.put(2, new ConferenceRoomRequest(2, new Time(1, 0, 0), "meeting"));
    conferenceRoomRequestHash.put(3, new ConferenceRoomRequest(3, new Time(2, 0, 0), "party"));
    conferenceRoomRequestHash.put(4, new ConferenceRoomRequest(4, new Time(4, 0, 0), "party"));
    conferenceRoomRequestHash.put(5, new ConferenceRoomRequest(5, new Time(3, 0, 0), "party"));

    HashMap<Integer, ConferenceRoomRequest> allConferenceRequests =
        conferenceRoomRequestDAO.getAll();
    assertEquals(allConferenceRequests, conferenceRoomRequestHash);
  }

  @Test
  public void testGetAllMealRequest() throws SQLException {
    mealHash.put(1, new MealRequest(1, "tom", "mac and cheese", "none"));
    mealHash.put(2, new MealRequest(2, "jerry", "steak", "medium"));
    mealHash.put(3, new MealRequest(3, "phil", "chicken and rice", "add bbq sauce"));

    HashMap<Integer, MealRequest> allMealRequests = mealRequestDAO.getAll();
    assertEquals(allMealRequests, mealHash);
  }

  @Test
  public void testGetAllEdges() throws SQLException {

    Edge edge1 = new Edge(1, 10);
    Edge edge2 = new Edge(2, 12);
    Edge edge3 = new Edge(3, 14);
    Edge edge4 = new Edge(4, 16);
    Edge edge5 = new Edge(5, 18);

    edge1.setEdgeID("1_10");
    edge1.setEdgeID("2_12");
    edge1.setEdgeID("3_14");
    edge1.setEdgeID("4_16");
    edge1.setEdgeID("5_18");

    edgeHash.put(edge1.getEdgeID(), new Edge(1, 10));
    edgeHash.put(edge2.getEdgeID(), new Edge(2, 12));
    edgeHash.put(edge3.getEdgeID(), new Edge(3, 14));
    edgeHash.put(edge4.getEdgeID(), new Edge(4, 16));
    edgeHash.put(edge5.getEdgeID(), new Edge(5, 18));

    HashMap<String, Edge> allEdges = EdgeDAO.getAll();
    assertEquals(allEdges, edgeHash);
  }

  @Test
  public void testGetAllFlowerRequest() throws SQLException {
    flowerRequestHash.put(1, new FlowerRequest(1, "tulip", 5, "john", "none"));
    flowerRequestHash.put(2, new FlowerRequest(2, "rose", 4, "amy", "bring with joy"));
    flowerRequestHash.put(3, new FlowerRequest(3, "daisy", 23, "steve", "none"));
    flowerRequestHash.put(4, new FlowerRequest(4, "rose", 6, "austin", "none"));
    flowerRequestHash.put(5, new FlowerRequest(5, "rose", 7, "micheal", "none"));

    HashMap<Integer, FlowerRequest> allFlowers = flowerRequestDAO.getAll();
    assertEquals(flowerRequestHash, allFlowers);
  }

  @Test
  public void testGetAllAccounts() throws SQLException {
    accountHashMap.put(1, new Account(1, "pass1", true));
    accountHashMap.put(2, new Account(2, "pass2", false));
    accountHashMap.put(3, new Account(3, "pass3", false));

    HashMap<Integer, Account> allAccounts = accountDAO.getAll();
    assertEquals(accountHashMap, allAccounts);
  }

  @Test
  public void testGetAllEmployees() throws SQLException {
    employeeHashMap.put(1, new Employee(1, "john", "barry", "jb@gmail.com", "yes"));
    employeeHashMap.put(2, new Employee(2, "tom", "doe", "td@gmail.com", "no"));
    employeeHashMap.put(3, new Employee(3, "micheal", "smith", "ms@gmail.com", "yes"));
    employeeHashMap.put(4, new Employee(4, "stephen", "jackson", "sj@gmail.com", "no"));
    employeeHashMap.put(5, new Employee(5, "austin", "meyers", "am@gmail.com", "yes"));

    HashMap<Integer, Employee> allEmployees = employeeDAO.getAll();
    assertEquals(employeeHashMap, allEmployees);
  }

  @Test
  public void testGetAllRequest() throws SQLException {
    StatusTypeEnum status1 = StatusTypeEnum.valueOf("done");
    Request r1 =
        new Request(
            1,
            3,
            "Conference Room",
            status1,
            "Paul",
            new java.util.Date(2023, 02, 05),
            new Time(12, 0, 0));

    requestHash.put(1, r1);
    requestHash.put(
        2,
        new Request(
            2,
            3,
            "Conference Room",
            "Paul",
            "done",
            new java.sql.Date(2023, 02, 05),
            new Time(12, 0, 0)));
    requestHash.put(
        3,
        new Request(
            3,
            3,
            "Conference Room",
            "Paul",
            "done",
            new java.sql.Date(2023, 02, 05),
            new Time(12, 0, 0)));

    HashMap<Integer, Request> allRequest = requestDAO.getAll();
    assertEquals(requestHash, allRequest);
  }

  @Test
  public void testGetAllLocationNames() throws SQLException {

    LocationName loc1 = new LocationName("Hall 1 Level 2", "Hall", "HALL");
    LocationName loc2 = new LocationName("BTM Conference Center", "BTM Conference", "CONF");
    LocationName loc3 = new LocationName("Neuroscience Waiting Room", "Neuro Waiting Room", "DEPT");
    LocationName loc4 =
        new LocationName("Orthopedics and Rhemutalogy", "Orthopedics and Rhemutalogy", "DEPT");
    LocationName loc5 = new LocationName("MS Waiting", "MS Waiting", "DEPT");

    locationNameHashMap.put(loc1.getLongName(), new LocationName("Hall 1 Level 2", "Hall", "HALL"));
    locationNameHashMap.put(
        loc2.getLongName(), new LocationName("BTM Conference Center", "BTM Conference", "CONF"));
    locationNameHashMap.put(
        loc3.getLongName(),
        new LocationName("Neuroscience Waiting Room", "Neuro Waiting Room", "DEPT"));
    locationNameHashMap.put(
        loc4.getLongName(),
        new LocationName("Orthopedics and Rhemutalogy", "Orthopedics and Rhemutalogy", "DEPT"));
    locationNameHashMap.put(
        loc5.getLongName(), new LocationName("MS Waiting", "MS Waiting", "DEPT"));

    HashMap<String, LocationName> allLocationNames = nameDAO.getAll();
    assertEquals(locationNameHashMap, allLocationNames);
  }

  // Test  insert() methods

  // Test delete() delete

}
