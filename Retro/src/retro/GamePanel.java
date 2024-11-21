package retro;

import entity.Player;
//import gameStateManager.GameStateManager;
import map.Map;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16; //16
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576

    final int FPS = 60;

    //TileMaganer tileM = new TileMaganer(this);
    //SYSTEM
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);


    //GameStateManager gStateManager = new GameStateManager(keyH);
    Player player = new Player(this, keyH);

    Map map = new Map(this, player);
    UI ui = new UI(this);
    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


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
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public void setupGame(){
        gameState = playState;
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
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta > 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


            //System.out.println("running");
           // update();

           // repaint();
            
        }
        

        //throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public void update(){
        if(gameState == playState){
            player.update();
            map.update(screenWidth, screenHeight, player.x, player.y);
        }
        if(gameState == pauseState){
        }
        System.out.println("gameState: " + gameState);
/* 
        gStateManager.update();

        if(gStateManager.isRunning()){
            player.update();
            map.update(screenWidth, screenHeight, player.x, player.y);
        }
            */
    //    player.update();
    //    map.update(screenWidth, screenHeight, player.x, player.y);
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        map.draw(g2);
        player.draw(g2);
        ui.draw(g2);

        g2.dispose();

    }
    
}
