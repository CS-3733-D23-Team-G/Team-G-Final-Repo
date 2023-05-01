package edu.wpi.teamg.controllers;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KidsGamePageController {

  Image pacBoxArt = new Image("edu/wpi/teamg/Images/PacmanBoxArt.png");
  Image pongBoxArt = new Image("edu/wpi/teamg/Images/SouthArrow.png");
  Image snakeBoxArt = new Image("edu/wpi/teamg/Images/SnakeBoxArt.jpeg");
  Image tttBoxArt = new Image("edu/wpi/teamg/Images/TicTacToeBoxArt.jpg");

  @FXML ImageView snake = new ImageView(snakeBoxArt);
  @FXML ImageView ticTacToe = new ImageView(tttBoxArt);
  @FXML ImageView pong = new ImageView(pongBoxArt);
  @FXML ImageView pac = new ImageView(pacBoxArt);

  @FXML
  public void initialize() throws SQLException {
    snake.setOnMouseClicked(event -> snakeGame());
    ticTacToe.setOnMouseClicked(event -> ticTacToeGame());
    pong.setOnMouseClicked(event -> pongGame());
    pac.setOnMouseClicked(event -> pacmanGame());
  }

  public void snakeGame() {
    new GameFrameSnake();
  }

  public void ticTacToeGame() {
    new GameTicTacToe();
  }

  public void pongGame() {
    new GameFramePong();
  }

  public void pacmanGame() {
    new GamePacManFrame();
  }
}
