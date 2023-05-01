package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.nodeDAO;

import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class AnimatedPopOverController {

  @FXML TextArea anitxt;

  @FXML MFXButton back;

  @FXML MFXButton next;

  ArrayList<String> path;

  String txtForPath;

  boolean start = true;

  boolean end = false;

  Pane pane;

  int index;

  public void initialize() {
    anitxt.setEditable(false);

    next.setOnMouseClicked(
        event -> {
          try {
            goToNextEdge();
          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });

    back.setOnMouseClicked(event -> goBackEdge());
  }

  public void setTxtForPath(String txtPath, Pane nPane) {
    anitxt.setText(txtPath);
    this.pane = nPane;
    pane.getChildren().clear();
  }

  public void getThePath(ArrayList<String> path) {
    this.path = path;
  }

  public void goToNextEdge() throws IOException, SQLException {

    pane.getChildren().clear();

    setPath(path);
    index = index + 1;
    if (index == path.size() - 1) {
      next.setVisible(false);
    } else {
      next.setVisible(true);
      back.setVisible(true);
    }
    System.out.println("going up + " + index);
  }

  public void setPath(ArrayList<String> path) throws SQLException {
    //    ArrayList<String> aniPath = new ArrayList<>();
    //
    //    for (int i = 0; i < index; i++) {
    //      aniPath.add(this.path.get(i));
    //    }
    //
    //    var loader = new FXMLLoader(App.class.getResource("views/pathfinding.fxml"));
    //    pathfindingController controller = loader.getController();
    //
    //    controller.nodePane.getChildren().clear();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    ArrayList<String> pathForFloor = new ArrayList<>();
    Circle fPoint = new Circle();
    javafx.scene.shape.Polygon triangle = new Polygon();
    Circle start = new Circle();
    Circle end = new Circle();

    for (int i = 0; i < index; i++) {
      int finalI = i;

      Circle point =
          new Circle(
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
              10,
              javafx.scene.paint.Color.rgb(1, 45, 90));
      if (i + 1 != path.size()
          && !Objects.equals(
              nodes.get(Integer.parseInt(path.get(i))).getFloor(),
              nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {
        triangle.setFill(javafx.scene.paint.Color.rgb(246, 189, 56));
        point.setRadius(30);

        if (getFloorIndex(nodes.get(Integer.parseInt(path.get(i))).getFloor())
            < getFloorIndex(nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() + 10,
                  point.getCenterX(),
                  point.getCenterY() - point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() + 10);
        } else {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() - 10,
                  point.getCenterX(),
                  point.getCenterY() + point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() - 10);
        }
        pane.getChildren().add(triangle);
        pathForFloor.add(path.get(i));
        triangle.setOnMouseClicked(
            event -> {
              try {
                nextFloor(nodes.get(Integer.parseInt(path.get(finalI + 1))), path, finalI + 1);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
        break;
      } else {

        if (i == 0) {
          point.setFill(javafx.scene.paint.Color.rgb(0, 156, 166));
          point.setRadius(20);

          start = point;
          pane.getChildren().add(start);
          pathForFloor.add(path.get(i));
        } else if (i + 1 == path.size()) {
          point.setFill(javafx.scene.paint.Color.rgb(0, 156, 166));
          point.setRadius(20);

          end = point;
          pane.getChildren().add(end);
          pathForFloor.add(path.get(i));
        } else {
          pane.getChildren().add(point);
          pathForFloor.add(path.get(i));
        }
      }
    }

    for (int i = 1; i < pathForFloor.size(); i++) {
      Line pathLine =
          new Line(
              nodes.get(Integer.parseInt(path.get(i - 1))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i - 1))).getYcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord());
      pathLine.setStrokeWidth(10);
      pathLine.setStroke(Color.rgb(1, 45, 90));
      pane.getChildren().add(pathLine);
    }

    triangle.toFront();
    start.toFront();
    end.toFront();
  }

  public void nextFloor(Node node, ArrayList<String> path, int index) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    ArrayList<String> pathForFloor = new ArrayList<>();
    Polygon triangle = new Polygon();
    Circle downPoint = new Circle();
    Circle end = new Circle();

    String floor = node.getFloor();

    for (int i = index; i < path.size(); i++) {
      int finalI = i;

      if (i == index) {
        downPoint =
            new Circle(
                nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
                nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
                20,
                Color.rgb(246, 189, 56));

        pane.getChildren().add(downPoint);
        downPoint.setOnMouseClicked(
            event -> {
              try {
                setPath(path);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
      }
      Circle point =
          new Circle(
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
              10,
              Color.rgb(1, 45, 90));
      if (i + 1 != path.size()
          && !Objects.equals(
              nodes.get(Integer.parseInt(path.get(i))).getFloor(),
              nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {

        point.setRadius(30);
        if (getFloorIndex(nodes.get(Integer.parseInt(path.get(i))).getFloor())
            < getFloorIndex(nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() + 10,
                  point.getCenterX(),
                  point.getCenterY() - point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() + 10);
        } else {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() - 10,
                  point.getCenterX(),
                  point.getCenterY() + point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() - 10);
        }
        triangle.setFill(Color.rgb(246, 189, 56));
        pane.getChildren().add(triangle);
        pathForFloor.add(path.get(i));
        triangle.setOnMouseClicked(
            event -> {
              try {
                nextFloor(nodes.get(Integer.parseInt(path.get(finalI + 1))), path, finalI + 1);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
        break;
      } else {
        if (i == (path.size() - 1)) {
          point.setFill(Color.rgb(0, 156, 166));
          point.setRadius(20);
          end = point;
          pane.getChildren().add(end);
          pathForFloor.add(path.get(i));
        } else {

          pane.getChildren().add(point);
          pathForFloor.add(path.get(i));
        }
      }
    }

    for (int i = 1; i < pathForFloor.size(); i++) {
      Line pathLine =
          new Line(
              nodes.get(Integer.parseInt(path.get(index + i - 1))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(index + i - 1))).getYcoord(),
              nodes.get(Integer.parseInt(path.get(index + i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(index + i))).getYcoord());
      pathLine.setStrokeWidth(10);
      pathLine.setStroke(Color.rgb(1, 45, 90));
      pane.getChildren().add(pathLine);
    }

    downPoint.toFront();
    triangle.toFront();
    end.toFront();
  }

  public int getFloorIndex(String floor) {
    int floorIndex = 0;
    switch (floor) {
      case "L2":
        floorIndex = 1;
        break;
      case "1 ":
        floorIndex = 2;
        break;
      case "2 ":
        floorIndex = 3;
        break;
      case "3 ":
        floorIndex = 4;
        break;
    }

    return floorIndex;
  }

  public void goBackEdge() {

    index = index - 1;
    if (index == 0) {
      back.setVisible(false);
    } else {
      back.setVisible(true);
      next.setVisible(true);
    }

    System.out.println("going down + " + index);
  }
}
