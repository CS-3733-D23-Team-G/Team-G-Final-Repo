/*-------------------------*/
/* DO NOT DELETE THIS TEST */
/*-------------------------*/

package edu.wpi.teamg;

// import edu.wpi.teamg.pathFinding.Node;

import edu.wpi.teamg.DAOs.*;
import edu.wpi.teamg.ORMClasses.*;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.Test;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  HashMap<Integer, Move> moveHash = new HashMap<Integer, Move>();

  LocationNameDAO nameDAO= new LocationNameDAO();
  HashMap<String, LocationName> locationNameHashMap = new HashMap<String, LocationName>();

  EmployeeDAO employeeDAO = new EmployeeDAO();
  HashMap<Integer, Employee> employeeHashMap = new HashMap<Integer,Employee>();

   AccountDAO accountDAO = new AccountDAO();
   HashMap<Integer, Account> accountHashMap = new HashMap<Integer,Account>();

  ConferenceRoomRequestDAO conferenceRoomRequestDAO = new ConferenceRoomRequestDAO();
  HashMap<Integer, ConferenceRoomRequest> conferenceRoomRequestHash = new HashMap<Integer, ConferenceRoomRequest>();

  FlowerRequestDAO flowerRequestDAO = new FlowerRequestDAO();
  HashMap<Integer, FlowerRequest> flowerRequestHash = new HashMap<Integer, FlowerRequest>();

  MealRequestDAO mealRequestDAO = new MealRequestDAO();
  HashMap<Integer, MealRequest> mealHash = new HashMap<Integer, MealRequest>();

  RequestDAO requestDAO = new RequestDAO();
  HashMap<Integer, Request> requestHash = new HashMap<Integer, Request>();


  // Test getAll() methods

  @Test
  public void testGetAllNodes() throws SQLException {
    nodeHash.put(1, new Node(1,2265,904,"L1","45 Francis"));
    nodeHash.put(2, new Node(2,2130,849,"L2","Tower"));
    nodeHash.put(3, new Node(3,2360,849,"L1","45 Francis"));
    HashMap <Integer,Node> allNodes = nodeDAO.getAll();
    assertEquals(allNodes,nodeHash);
  }

//  @Test
//  public void testGetAllConferenceRoomRequest() throws SQLException {
//   conferenceRoomRequestHash.put(1, new ConferenceRoomRequest(1,12:30,meeting);
//   conferenceRoomRequestHash.put(2, new ConferenceRoomRequest(2,13:00,meeting);
//   conferenceRoomRequestHash.put(3, new ConferenceRoomRequest(3,14:00,party);
//   conferenceRoomRequestHash.put(4, new ConferenceRoomRequest(4,16:00,party);
//   conferenceRoomRequestHash.put(5, new ConferenceRoomRequest(5,15:30,party);
//
//   HashMap<Integer, ConferenceRoomRequest> allConferenceRequests = conferenceRoomRequestDAO.getAll();
//   assertEquals(allConferenceRequests,conferenceRoomRequestHash);
//  }

//  @Test
//  public void testGetAllMealRequest() throws SQLException {
//   mealHash.put(1, new MealRequest(1,tom,mac and cheese,none));
//    mealHash.put(2, new MealRequest(2,jerry,steak,medium));
//    mealHash.put(3, new MealRequest(3,phil,chicken and rice,add bbq sauce));
//
//    HashMap<Integer,MealRequest> allMealRequests = mealRequestDAO.getAll();
//    assertEquals(allMealRequests,mealHash);
//  }

  @Test
  public void testGetAllEdges() throws SQLException {

    Edge edge1 = new Edge(1,10);
    Edge edge2 = new Edge(2,12);
    Edge edge3 = new Edge(3,14);
    Edge edge4 = new Edge(4,16);
    Edge edge5 = new Edge(5,18);

   edge1.setEdgeID("1_10");
    edge1.setEdgeID("2_12");
    edge1.setEdgeID("3_14");
    edge1.setEdgeID("4_16");
    edge1.setEdgeID("5_18");

    edgeHash.put(edge1.getEdgeID(), new Edge(1,10));
    edgeHash.put(edge2.getEdgeID(), new Edge(2,12));
    edgeHash.put(edge3.getEdgeID(),new Edge(3,14));
    edgeHash.put(edge4.getEdgeID(),new Edge(4,16));
    edgeHash.put(edge5.getEdgeID(),new Edge(5,18));

    HashMap<String, Edge> allEdges = EdgeDAO.getAll();
    assertEquals(allEdges,edgeHash);
  }

//  @Test
//  public void testGetAllFlowerRequest() throws SQLException {
//   flowerRequestHash.put(1, new FlowerRequest(1,tulip,5,john,none));
//   flowerRequestHash.put(2, new FlowerRequest(2,rose,4,amy,bring with joy));
//   flowerRequestHash.put(3, new FlowerRequest(3,daisy,23,steve,none));
//   flowerRequestHash.put(4, new FlowerRequest(4,rose,6,austin,none));
//   flowerRequestHash.put(5, new FlowerRequest(5,rose,7,micheal,none));
//
//   HashMap<Integer, FlowerRequest> allFlowers = flowerRequestDAO.getAll();
//   assertEquals(flowerRequestHash,allFlowers);
//  }

  @Test
  public void testGetAllAccounts() throws SQLException {

  }

  public void testGetAllEmployees() throws SQLException{
      employeeHashMap.put(1,new Employee(1,"john","barry","jb@gmail.com", "yes"));
      employeeHashMap.put(2,new Employee(2,"tom","doe","td@gmail.com", "no"));
      employeeHashMap.put(3,new Employee(3,"micheal","smith","ms@gmail.com", "yes"));
      employeeHashMap.put(4,new Employee(4,"stephen","jackson","sj@gmail.com", "no"));
      employeeHashMap.put(5,new Employee(5,"austin","meyers","am@gmail.com", "yes"));

      HashMap <Integer,Employee> allEmployees = employeeDAO.getAll();
      assertEquals(employeeHashMap,allEmployees);
  }

  @Test
  public void testGetAllRequest() throws SQLException {

  }

  @Test
  public void testGetAllLocationNames() throws SQLException {

      LocationName loc1 = new LocationName("Hall 1 Level 2","Hall","HALL");
      LocationName loc2 = new LocationName("BTM Conference Center","BTM Conference","CONF");
      LocationName loc3 = new LocationName("Neuroscience Waiting Room","Neuro Waiting Room","DEPT");
      LocationName loc4 = new LocationName("Orthopedics and Rhemutalogy","Orthopedics and Rhemutalogy","DEPT");
      LocationName loc5 = new LocationName("MS Waiting","MS Waiting","DEPT");


      locationNameHashMap.put(loc1.getLongName(),new LocationName("Hall 1 Level 2","Hall","HALL"));
      locationNameHashMap.put(loc2.getLongName(),new LocationName("BTM Conference Center","BTM Conference","CONF"));
      locationNameHashMap.put(loc3.getLongName(),new LocationName("Neuroscience Waiting Room","Neuro Waiting Room","DEPT"));
      locationNameHashMap.put(loc4.getLongName(),new LocationName("Orthopedics and Rhemutalogy","Orthopedics and Rhemutalogy","DEPT"));
      locationNameHashMap.put(loc5.getLongName(),new LocationName("MS Waiting","MS Waiting","DEPT"));

      HashMap <String,LocationName> allLocationNames = nameDAO.getAll();
      assertEquals(locationNameHashMap,allLocationNames);
  }



  // Test  insert() methods



  // Test delete() delete


}
