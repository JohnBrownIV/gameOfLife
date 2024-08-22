import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class MyPanel extends JPanel implements ActionListener {

Timer timer;
int[][] tiles;
int[][] tilesBuffer;
int tileWidth;
int tileHeight;
ArrayList<Integer> occupiedX;
ArrayList<Integer> occupiedY;
 
 MyPanel(){
  
  //image = new ImageIcon("sky.png").getImage();
  int startWidth = 1300;
  int startHeight = 800;
  occupiedX = new ArrayList<Integer>();
  occupiedY = new ArrayList<Integer>();
  this.setPreferredSize(new Dimension(startWidth,startHeight));
  tileWidth = startWidth/10;
  tileHeight = startHeight/10;
  tiles = new int[tileWidth][tileHeight];
  //Trying to make a sample floater
  tiles[5][5] = 1;
  tiles[6][6] = 1;
  tiles[6][7] = 1;
  tiles[5][7] = 1;
  tiles[4][7] = 1;
  tilesBuffer = new int[tileWidth][tileHeight];
  //(int)(Math.random() * tileWidth) + 1//This is the random code for width I guess
  for (int i = 0; i < 50; i++) {
    tiles[(int)(Math.random() * tileWidth) + 0][(int)(Math.random() * tileHeight) + 0] = 1;
  }
  timer = new Timer(300, this);
	timer.start();

 }
 
 public void paint(Graphics g) {
  
  Graphics2D g2D = (Graphics2D) g;

  g2D.setPaint(Color.black);

  g2D.fillRect(0, 0, 1300, 800);

  g2D.setPaint(Color.white);
  g2D.setFont(new Font("Comic Sans MS",Font.BOLD,10));

  if (occupiedX.size() > 0) {
    for (int i = 0; i < occupiedX.size(); i++) {
      g2D.setPaint(Color.white);
      g2D.fillRect(occupiedX.get(i) * 10, occupiedY.get(i) * 10,10,10);
      g2D.setPaint(Color.red);
      g2D.drawString("E",occupiedX.get(i) * 10, occupiedY.get(i) * 10 + 10);
    }
  }


  
 }
  @Override
	public void actionPerformed(ActionEvent e) {
    occupiedX.clear();
    occupiedY.clear();
    tilesBuffer = tiles;
    for (int x = 0; x < tileWidth; x++) {
      for (int y = 0; y < tileHeight; y++) {
        frameCheck(x,y);
      }
    }
    tiles = tilesBuffer;
    repaint();
  }

  public int surroundCount(int xTile, int yTile) {
    int total = 0;
    if ((xTile > 1 && xTile < tileWidth - 1) && (yTile > 1 && yTile < tileHeight - 1)) {
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
    return total;
  }
  public void frameCheck(int xTile, int yTile) {
    boolean alive = false;
    int surround = surroundCount(xTile,yTile);
    if (tiles[xTile][yTile] == 1) {
      alive = true;
      //System.out.println(surround + " " + xTile + " " + yTile);
    }
    if (alive) {
      if (surround == 2 || surround == 3) {
        alive = true;
      } else {
        alive = false;
      }
    } else if (surround == 3) {
      alive = true;
    }
    if (alive) {
      tilesBuffer[xTile][yTile] = 1;
      occupiedX.add(xTile);
      occupiedY.add(yTile);
    } else {
      tilesBuffer[xTile][yTile] = 0;
    }
  }
}