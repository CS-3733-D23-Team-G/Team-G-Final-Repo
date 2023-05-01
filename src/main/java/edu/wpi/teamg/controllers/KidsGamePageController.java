package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class KidsGamePageController {
  @FXML MFXButton snake;
  @FXML MFXButton ticTacToe;
  @FXML MFXButton pong;
  @FXML MFXButton pac;

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
