import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class MyFrame extends JFrame implements KeyListener {
 
 MyPanel panel;
 
 MyFrame(int players){
  panel = new MyPanel();
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  this.add(panel);
  this.pack();
  this.setLocationRelativeTo(null);
  this.addKeyListener(this);
  this.setVisible(true);
  this.toFront();
  this.requestFocus();
  
 }
 @Override
	public void keyTyped(KeyEvent e) {
  }
 @Override
	public void keyPressed(KeyEvent e) {
    //System.out.println(e.getKeyCode());
    if (e.getKeyCode() == 27) {
      panel.togglePause();
    } else if (e.getKeyCode() == 49) {
      panel.toggleSpeed();
    } else if (e.getKeyCode() == 50) {
      panel.switchPlaceMode(1);
    } else if (e.getKeyCode() == 51) {
      panel.switchPlaceMode(2);
    }
  }
  @Override
	public void keyReleased(KeyEvent e) {
  }
}