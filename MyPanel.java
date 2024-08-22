import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyPanel extends JPanel implements ActionListener {

Timer timer;
int[][] tiles;
int[][] tilesBuffer;
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
  //Trying to make a sample floater
  tiles[30][30] = 1;
  tiles[32][30] = 1;
  tiles[31][31] = 1;
  tiles[32][31] = 1;
  tiles[31][32] = 1;
  //Blinker
  tiles[60][60] = 1;
  tiles[61][60] = 1;
  tiles[62][60] = 1;
  tilesBuffer = new int[tileWidth][tileHeight];
  //(int)(Math.random() * tileWidth) + 1//This is the random code for width I guess
  /*for (int i = 0; i < 50; i++) {
    tiles[(int)(Math.random() * tileWidth) + 0][(int)(Math.random() * tileHeight) + 0] = 1;
  }*/
  timer = new Timer(500, this);
	timer.start();

 }
 
 public void paint(Graphics g) {
  
  Graphics2D g2D = (Graphics2D) g;

  g2D.setPaint(Color.black);

  g2D.fillRect(0, 0, 1300, 800);

  g2D.setPaint(Color.white);
  g2D.setFont(new Font("Comic Sans MS",Font.BOLD,10));

  for (int x = 0; x < tileWidth; x++) {
    for (int y = 0; y < tileHeight; y++) {
      if (tiles[x][y] == 1) {
        g2D.setPaint(Color.white);
        g2D.fillRect(x * 10, y * 10, 10, 10);
        /*if (surroundCount(x,y) < 2 || surroundCount(x,y) > 3) {//This is for debugging
          g2D.setPaint(Color.red);
          g2D.fillRect((x * 10) + 2, (y * 10) + 2, 6, 6);
        }
        g2D.setPaint(Color.blue);
        g2D.drawString(String.valueOf(surroundCount(x,y)),(x * 10) + 3, (y * 10) + 10);*/
      } else {
        /*g2D.setPaint(Color.blue); //This is for debugging
        g2D.fillRect((x * 10) + 2, (y * 10) + 2, 6, 6);
        if (surroundCount(x,y) == 3) {
          g2D.setPaint(Color.green);
          g2D.fillRect((x * 10) + 2, (y * 10) + 2, 6, 6);
        }
        g2D.setPaint(Color.red);
        g2D.drawString(String.valueOf(surroundCount(x,y)),(x * 10) + 3, (y * 10) + 10);*/
      }
    }
  }


  
 }
  @Override
  //Timer event that triggers event
	public void actionPerformed(ActionEvent e) {
    for (int x = 0; x < tileWidth; x++) {
      for (int y = 0; y < tileHeight; y++) {
        frameCheck(x,y);
      }
    }
    repaint();
    //tiles = Arrays.copyOf(tilesBuffer,tilesBuffer.length);
    buffToTile();
  }

  public int surroundCount(int xTile, int yTile) {
    int total = 0;
    if ((xTile > 1) && (xTile < tileWidth - 1) && (yTile > 1) && (yTile < tileHeight - 1)) {
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
      } else if (surround == 1) {
        alive = false;
      } else if (surround >= 4) {
        alive = false;
      }
    } else if (surround == 3) {
      alive = true;
    }
    if (alive) {
      tilesBuffer[xTile][yTile] = 1;
    } else {
      tilesBuffer[xTile][yTile] = 0;
    }
  }
  public void buffToTile() {
    for (int x = 0; x < tileWidth; x++) {
      for (int y = 0; y < tileHeight; y++) {
        tiles[x][y] = tilesBuffer[x][y];
      }
    }
  }
}