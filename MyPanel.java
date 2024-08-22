import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements ActionListener {

Timer timer;
Boolean[][] tiles;
int tileWidth;
int tileHeight;
 
 MyPanel(){
  
  //image = new ImageIcon("sky.png").getImage();
  int startWidth = 1300;
  int startHeight = 800;
  this.setPreferredSize(new Dimension(startWidth,startHeight));
  tileWidth = startWidth/10;
  tileHeight = startHeight/10;
  tiles = new Boolean[tileWidth][tileHeight];
  //(int)(Math.random() * tileWidth) + 1//This is the random code for width I guess
  for (int i = 0; i < 50; i++) {
    tiles[(int)(Math.random() * tileWidth) + 0][(int)(Math.random() * tileHeight) + 0] = true;
  }
  timer = new Timer(5, this);
	timer.start();

 }
 
 public void paint(Graphics g) {
  
  Graphics2D g2D = (Graphics2D) g;

  g2D.setPaint(Color.black);

  g2D.fillRect(0, 0, 1300, 800);

  g2D.setPaint(Color.white);

  g2D.setFont(new Font("Comic Sans MS",Font.BOLD,20));
  
 }
  @Override
	public void actionPerformed(ActionEvent e) {
    repaint();
  }
}