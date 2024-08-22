import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements ActionListener {

Timer timer;
int[][] tiles;
int tileWidth;
int tileHeight;
 
 MyPanel(){
  
  //image = new ImageIcon("sky.png").getImage();
  int startWidth = 1300;
  int startHeight = 800;
  this.setPreferredSize(new Dimension(startWidth,startHeight));
  tileWidth = startWidth/10;
  tileHeight = startHeight/10;
  tiles = new int[tileWidth][tileHeight];
  //(int)(Math.random() * tileWidth) + 1//This is the random code for width I guess
  for (int i = 0; i < 50; i++) {
    tiles[(int)(Math.random() * tileWidth) + 0][(int)(Math.random() * tileHeight) + 0] = 1;
  }
  timer = new Timer(5, this);
	timer.start();

 }
 
 public void paint(Graphics g) {
  
  Graphics2D g2D = (Graphics2D) g;

  g2D.setPaint(Color.black);

  g2D.fillRect(0, 0, 1300, 800);

  g2D.setPaint(Color.white);

  for (int x = 0; x < tileWidth; x++) {
    for (int y = 0; y < tileHeight; y++) {
      if (tiles[x][y] == 1) {
        g2D.fillRect((x) * 10, (y) * 10, 10, 10);
      }
    }
  }

  g2D.setFont(new Font("Comic Sans MS",Font.BOLD,20));
  
 }
  @Override
	public void actionPerformed(ActionEvent e) {
    repaint();
  }

  public int surroundCount(int xTile, int yTile) {
    int total = 0;
    if ((xTile != 0 && xTile != tileWidth) && (yTile != 1 && yTile != tileHeight)) {
      //Bottom Row
      total += tiles[xTile - 1][yTile - 1];
      total += tiles[xTile][yTile - 1];
      total += tiles[xTile + 1][yTile - 1];
      //Top Row
      total += tiles[xTile - 1][yTile + 1];
      total += tiles[xTile][yTile + 1];
      total += tiles[xTile + 1][yTile + 1];
      //Middle
      total += tiles[xTile - 1][yTile];
      total += tiles[xTile + 1][yTile];
    }
  }
}