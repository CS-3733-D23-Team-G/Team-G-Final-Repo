package edu.wpi.teamg.controllers;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFramePong extends JFrame {

  GamePanelPong panel;

  GameFramePong() {
    panel = new GamePanelPong();
    this.add(panel);
    this.setTitle("Pong Game");
    this.setResizable(false);
    this.setBackground(Color.black); // Background Color
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}
