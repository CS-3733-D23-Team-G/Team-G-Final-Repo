package edu.wpi.teamg.controllers;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePongPaddle extends Rectangle {
  int id;
  int yVelocity;
  int speed = 10;

  GamePongPaddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
    super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    this.id = id;
  }

  public void keyPressed(KeyEvent e) {
    switch (id) {
      case 1:
        if (e.getKeyCode() == KeyEvent.VK_W) {
          setyDirection(-speed);
          move();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
          setyDirection(speed);
          move();
        }
        break;

      case 2:
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          setyDirection(-speed);
          move();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          setyDirection(speed);
          move();
        }
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch (id) {
      case 1:
        if (e.getKeyCode() == KeyEvent.VK_W) {
          setyDirection(0);
          move();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
          setyDirection(0);
          move();
        }
        break;

      case 2:
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          setyDirection(0);
          move();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          setyDirection(0);
          move();
        }
        break;
    }
  }

  public void setyDirection(int yDirection) {
    yVelocity = yDirection;
  }

  public void move() {
    y = y + yVelocity;
  }

  public void draw(Graphics g) {
    if (id == 1) {
      g.setColor(Color.blue);
    } else {
      g.setColor(Color.red);
    }
    g.fillRect(x, y, width, height);
  }
}
