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
    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            panel.gameThread.stop();
          }
        });
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
  }
}
