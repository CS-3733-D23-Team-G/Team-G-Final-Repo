package edu.wpi.teamg.controllers;

import java.awt.*;
import javax.swing.*;

public class GamePacManFrame extends JFrame {

  public GamePacManFrame() {
    add(new GamePacManModel());
    this.setVisible(true);
    this.setTitle("Pacman");
    this.setSize(380, 420);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(null);
  }

  //  GamePacManModel panel;
  //
  //  GamePacManFrame() {
  //    panel = new GamePacManModel();
  //    this.add(panel);
  //    this.setTitle("Pacman");
  //    this.setSize(new Dimension(500, 500));
  //    //    this.setResizable(false);
  //    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  //    this.pack();
  //    this.setVisible(true);
  //    this.setLocationRelativeTo(null);
  //  }
}
