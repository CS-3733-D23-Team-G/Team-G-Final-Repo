package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.nodeDAO;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class AnimatedPopOverController {

  @FXML TextArea anitxt;

  @FXML MFXButton back;

  @FXML MFXButton next;

  ArrayList<String> path;

  String txtForPath;

  String[] textDirections;

  ArrayList<ImageView> imgs;

  boolean start = true;

  boolean end = false;

  int bottomAni = 0;

  int switchFloor = 0;
  Pane pane;

  int index = 1;
  int index2 = 1;

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

    back.setOnMouseClicked(
        event -> {
          try {
            goBackEdge();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void setTxtForPath(
      String txtPath, Pane nPane, ArrayList<javafx.scene.image.ImageView> img) {
    // anitxt.setText(txtPath);
    parseText(txtPath);
    this.pane = nPane;
    pane.getChildren().clear();
    imgs = img;
  }

  public void getThePath(ArrayList<String> path) {
    this.path = path;
  }

  public void parseText(String textPath) {
    textDirections = textPath.split("\n");

    for (int i = 0; i < textDirections.length; i++) {
      System.out.println(textDirections[i]);
    }
  }

  public void goToNextEdge() throws IOException, SQLException {

    pane.getChildren().clear();
    index = index + 1;

    if (switchFloor == 0) {
      bottomAni = 0;
      setPath(path);
    } else {

      nextFloor(
          App.allNodes.get(Integer.parseInt(path.get(switchFloor + 1))), path, switchFloor + 1);
      bottomAni = bottomAni + 1;
    }

    if (index == path.size()) {
      next.setVisible(false);
    } else {
      next.setVisible(true);
      back.setVisible(true);
    }
  }

  public void setPath(ArrayList<String> path) throws SQLException {
    pane.getChildren().clear();
    anitxt.clear();

    for (int i = 0; i < index; i++) {
      if (i == 0) {
        anitxt.setText(textDirections[i]);
      } else {
        anitxt.setText(anitxt.getText() + "\n" + textDirections[i]);
      }
    }

    String floor = App.allNodes.get(Integer.parseInt(path.get(0))).getFloor();
    switch (floor) {
      case "L1":
        imgs.get(0).setVisible(true);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(false);
        break;
      case "L2":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(true);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(false);
        break;
      case "1 ":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(true);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(false);
        break;
      case "2 ":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(true);
        imgs.get(4).setVisible(false);
        break;
      case "3 ":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(true);
        break;
    }
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
        switchFloor = finalI + 1;
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

      Timeline timeline =
          new Timeline(
              new KeyFrame(
                  Duration.seconds(0),
                  new KeyValue(
                      pathLine.endXProperty(),
                      nodes.get(Integer.parseInt(path.get(i - 1))).getXcoord()),
                  new KeyValue(
                      pathLine.endYProperty(),
                      nodes.get(Integer.parseInt(path.get(i - 1))).getYcoord())),
              new KeyFrame(
                  Duration.seconds(1.25),
                  new KeyValue(
                      pathLine.endXProperty(),
                      nodes.get(Integer.parseInt(path.get(i))).getXcoord()),
                  new KeyValue(
                      pathLine.endYProperty(),
                      nodes.get(Integer.parseInt(path.get(i))).getYcoord())));

      pathLine.setStroke(Color.rgb(1, 45, 90));
      pathLine.setStrokeWidth(10);
      timeline.setCycleCount(Timeline.INDEFINITE);

      pane.getChildren().add(pathLine);

      if (i == index - 1) {
        timeline.play();
      }
    }

    triangle.toFront();
    start.toFront();
    end.toFront();
  }

  public void nextFloor(Node node, ArrayList<String> path, int index) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    ArrayList<String> pathForFloor2 = new ArrayList<>();
    Polygon triangle = new Polygon();
    Circle downPoint = new Circle();
    Circle end = new Circle();

    String floor = App.allNodes.get(Integer.parseInt(path.get(index))).getFloor();
    switch (floor) {
      case "L1":
        imgs.get(0).setVisible(true);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(false);

        break;
      case "L2":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(true);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(false);

        break;
      case "1 ":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(true);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(false);

        break;
      case "2 ":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(true);
        imgs.get(4).setVisible(false);

        break;
      case "3 ":
        imgs.get(0).setVisible(false);
        imgs.get(1).setVisible(false);
        imgs.get(2).setVisible(false);
        imgs.get(3).setVisible(false);
        imgs.get(4).setVisible(true);

        break;
    }

    for (int i = index; i < this.index; i++) {
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
        pathForFloor2.add(path.get(i));
        switchFloor = finalI + 1;
        bottomAni = -1;
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
          pathForFloor2.add(path.get(i));
        } else {

          pane.getChildren().add(point);
          pathForFloor2.add(path.get(i));
        }
      }
    }

    for (int i = 1; i < pathForFloor2.size(); i++) {
      Line pathLine =
          new Line(
              nodes.get(Integer.parseInt(path.get(index + i - 1))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(index + i - 1))).getYcoord(),
              nodes.get(Integer.parseInt(path.get(index + i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(index + i))).getYcoord());

      Timeline timeline =
          new Timeline(
              new KeyFrame(
                  Duration.seconds(0),
                  new KeyValue(
                      pathLine.endXProperty(),
                      nodes.get(Integer.parseInt(path.get(index + i - 1))).getXcoord()),
                  new KeyValue(
                      pathLine.endYProperty(),
                      nodes.get(Integer.parseInt(path.get(index + i - 1))).getYcoord())),
              new KeyFrame(
                  Duration.seconds(1.25),
                  new KeyValue(
                      pathLine.endXProperty(),
                      nodes.get(Integer.parseInt(path.get(index + i))).getXcoord()),
                  new KeyValue(
                      pathLine.endYProperty(),
                      nodes.get(Integer.parseInt(path.get(index + i))).getYcoord())));

      pathLine.setStroke(Color.rgb(1, 45, 90));
      pathLine.setStrokeWidth(10);
      timeline.setCycleCount(Timeline.INDEFINITE);

      pane.getChildren().add(pathLine);

      System.out.println(bottomAni);
      System.out.println(i);
      if (i == bottomAni - 1) {
        timeline.play();
      }
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

  public void goBackEdge() throws SQLException {

    pane.getChildren().clear();
    index = index - 1;
    setPath(path);
    if (index == 0) {
      back.setVisible(false);
    } else {
      back.setVisible(true);
      next.setVisible(true);
    }
  }
}
