package edu.wpi.teamg.controllers;

import javax.swing.*;

public class GameFrame extends JFrame {

  public GameFrame() {

    this.add(new GamePanel());
    this.setTitle("Snake");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}
