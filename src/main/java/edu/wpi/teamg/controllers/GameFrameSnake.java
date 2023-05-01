package edu.wpi.teamg.controllers;

import javax.swing.*;

public class GameFrameSnake extends JFrame {

  public GameFrameSnake() {

    this.add(new GamePanelSnake());
    this.setTitle("Snake");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}
