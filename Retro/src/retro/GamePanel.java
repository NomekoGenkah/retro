package retro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16; //16
    final int scale = 3;
    
    final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768
    final int screenHeight = tileSize * maxScreenRow; //576

    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //default player xy
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLUE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta > 1) {
                update();
                repaint();
                delta--;
                
            }


            //System.out.println("running");
           // update();

           // repaint();
            
        }
        

        //throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public void update(){
        if(keyH.upPressed == true){
            playerY -= playerSpeed;   
        }
        if(keyH.downPressed == true){
            playerY += playerSpeed;   
        }
        if(keyH.leftPressed == true){
            playerX -= playerSpeed;   
        }
        if(keyH.rightPressed == true){
            playerX += playerSpeed;   
        }

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();

    }
    
}
