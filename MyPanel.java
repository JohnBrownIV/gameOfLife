import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyPanel extends JPanel implements ActionListener, MouseListener {

Timer timer;
int[][] tiles;
int[][] tilesBuffer;
int tileWidth;
int tileHeight;
int genAppears;
boolean running;
boolean highSpeed;
int placeMode;
 
 MyPanel(){
  
  //image = new ImageIcon("sky.png").getImage();
  int startWidth = 1300;
  int startHeight = 800;
  placeMode = 1;
  running = true;
  highSpeed = false;
  genAppears = 0;
  this.setPreferredSize(new Dimension(startWidth,startHeight));
  this.addMouseListener(this);
  tileWidth = startWidth/10;
  tileHeight = startHeight/10;
  tiles = new int[tileWidth][tileHeight];
  /*//Trying to make a sample floater
  tiles[30][30] = 1;
  tiles[32][30] = 1;
  tiles[31][31] = 1;
  tiles[32][31] = 1;
  tiles[31][32] = 1;
  //Blinker
  tiles[60][60] = 1;
  tiles[61][60] = 1;
  tiles[62][60] = 1;*/
  tilesBuffer = new int[tileWidth][tileHeight];
  /*for (int i = 0; i < 50; i++) {//Simple random generation
    tiles[(int)(Math.random() * tileWidth) + 0][(int)(Math.random() * tileHeight) + 0] = 1;
  }*/
  genClusters(50,5,5);
  timer = new Timer(500, this);
	timer.start();

 }
 
 public void paint(Graphics g) {
  
  Graphics2D g2D = (Graphics2D) g;

  g2D.setPaint(Color.black);

  g2D.fillRect(0, 0, tileWidth * 10, tileHeight * 10);

  g2D.setPaint(Color.white);
  g2D.setFont(new Font("Comic Sans MS",Font.BOLD,20));
  if (running != true) {
    g2D.drawString("PAUSED", (tileWidth * 10) - 100, 20);
  }
  g2D.drawString("Place Mode: " + placeMode, (tileWidth * 10) - 150, 40);
  if (genAppears > 0) {
    switch (genAppears) {
      case 3:
        g2D.setPaint(Color.white);
        break;
      case 2:
        g2D.setPaint(Color.gray);
        break;
      case 1:
        g2D.setPaint(Color.darkGray);
        break;
      default:
    }
    g2D.drawString("A new generation generates",0,20);
    genAppears--;
  }

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
    //Small chance to add a random cluster
    if ((Math.random() * 100) + 1 > 95) {
      genClustersActive(1,(int)(Math.random() * 10) + 5,(int)(Math.random() * 10) + 5);
      genAppears = 3;
    }
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
      } else {
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
  public void genClusters(int clusters, int density, int fill) {
    int clusterX;
    int clusterY;
    for (int i = 0; i < clusters; i++) {
      clusterX = (int)(Math.random() * (tileWidth - (density + 3))) + density;
      clusterY = (int)(Math.random() * (tileHeight - (density + 3))) + density;
      for (int k = 0; k < fill; k++) {
        tiles[(int)(Math.random() * density) + (clusterX - (density / 2))][(int)(Math.random() * density) + (clusterY - (density / 2))] = 1;
      }
    }
  }
  public void genClustersActive(int clusters, int density, int fill) {
    int clusterX;
    int clusterY;
    for (int i = 0; i < clusters; i++) {
      clusterX = (int)(Math.random() * (tileWidth - (density + 5))) + density;
      clusterY = (int)(Math.random() * (tileHeight - (density + 5))) + density;
      for (int k = 0; k < fill; k++) {
        try {
          tilesBuffer[(int)(Math.random() * density) + (clusterX - (density / 2))][(int)(Math.random() * density) + (clusterY - (density / 2))] = 1;
        }
        catch (ArrayIndexOutOfBoundsException e) {
          //It no worked
          //System.out.println("SAVED FROM ERRORS");
        }
      }
    }
  }
  public void switchPlaceMode(int direction) {
    if (direction == 2) {
      if (placeMode < 6) {
        placeMode++;
      } else {
        placeMode = 1;
      }
    } else {
      if (placeMode > 1) {
        placeMode--;
      } else {
        placeMode = 6;
      }
    }
    repaint();
  }
  public void spawnFloater(int inX, int inY, int direction) {
    switch (direction) {
      case 1: //Up right
        tiles[inX][inY] = 0;
        tiles[inX][inY + 1] = 1;
        tiles[inX + 1][inY] = 1;
        tiles[inX - 1][inY - 1] = 1;
        tiles[inX][inY - 1] = 1;
        tiles[inX + 1][inY - 1] = 1;
        break;
      case 2: //Down right
        tiles[inX][inY] = 0;
        tiles[inX][inY - 1] = 1;
        tiles[inX + 1][inY] = 1;
        tiles[inX - 1][inY + 1] = 1;
        tiles[inX][inY + 1] = 1;
        tiles[inX + 1][inY + 1] = 1;
        break;
      case 3: //Down Left
        tiles[inX][inY] = 0;
        tiles[inX][inY - 1] = 1;
        tiles[inX - 1][inY] = 1;
        tiles[inX + 1][inY + 1] = 1;
        tiles[inX][inY + 1] = 1;
        tiles[inX - 1][inY + 1] = 1;
        break;
      case 4: //Up Left
        tiles[inX][inY] = 0;
        tiles[inX][inY + 1] = 1;
        tiles[inX - 1][inY] = 1;
        tiles[inX + 1][inY - 1] = 1;
        tiles[inX][inY - 1] = 1;
        tiles[inX - 1][inY - 1] = 1;
        break;
      default:
    }
  }
  @Override
  public void mouseClicked(MouseEvent e) {

  }
  @Override
  public void mouseEntered(MouseEvent e) {

  }
  @Override
  public void mouseExited(MouseEvent e) {

  }
  @Override
  public void mousePressed(MouseEvent e) {
    try {
    switch (placeMode) {
      case 1://Single pixel
        tiles[e.getPoint().x / 10][e.getPoint().y / 10] = 1;
        break;
      case 2: //Floater up right
        spawnFloater(e.getPoint().x / 10, e.getPoint().y / 10,1);
        break;
      case 3: //Floater down right
        spawnFloater(e.getPoint().x / 10, e.getPoint().y / 10,2);
        break;
      case 4: //Floater down left
        spawnFloater(e.getPoint().x / 10, e.getPoint().y / 10,3);
        break;
      case 5: //Floater up left
        spawnFloater(e.getPoint().x / 10, e.getPoint().y / 10,4);
        break;
      case 6: //Clear pixel
        tiles[e.getPoint().x / 10][e.getPoint().y / 10] = 0;
        break;
      default:
    }
    } catch (ArrayIndexOutOfBoundsException b) {
      //System.out.println("SAVED FROM MOUSE ERROR");
    }
    repaint();
  }
  @Override
  public void mouseReleased(MouseEvent e) {
    //tiles[e.getPoint().x / 10][e.getPoint().y / 10] = 1;
    //repaint();
  }
  public void togglePause() {
    if (running) {
      running = false;
      timer.stop();
      repaint();
    } else {
      running = true;
      timer.start();
    }
  }
  public void toggleSpeed() {
    if (running == false) {
      running = true;
    }
    if (highSpeed) {
      timer.stop();
      timer = new Timer(500, this);
	    timer.start();
      highSpeed = false;
    } else {
      timer.stop();
      timer = new Timer(5, this);
	    timer.start();
      highSpeed = true;
    }
  }
}