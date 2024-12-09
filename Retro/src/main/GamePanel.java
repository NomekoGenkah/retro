package main;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import mapManager.MapManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements Runnable, MouseMotionListener {

    
    final int originalTileSize = 16; //16
    final int scale = 4;
    
    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576

    public final int FPS = 60;

    //ESTADOS DE JUEGO
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int winState = 3;

    boolean ganar = true;

    //system
    KeyHandler keyH = new KeyHandler(this);
    public Sound sound = new Sound();
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    AssetSetter aSetter = new AssetSetter(this);
    SaveManager saveM = new SaveManager(this);
    //player y entidades
    public Player player = new Player(this, keyH);
    public Entity[] entities = new Entity[6];

    //Mapa
    MapManager mapM = new MapManager(this, player);

    //UI
    UI ui = new UI(this);

    //trampas
    public CheatCodeHandler cheat = new CheatCodeHandler();


    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.addKeyListener(cheat);
        this.addMouseMotionListener(this);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                player.attack();
            }
        });
        

    }

    public void setupGame(){
        gameState = titleState;
        aSetter.setGreenSlime();
        playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        
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
        }
    }

    //logica
    public void update(){
        ganar = true;

        if(gameState == pauseState){
        //    saveM.saveGame();
        }
        //System.out.println(gameState);
        if(gameState == playState){
            //player.update();
            Rectangle daggerHitbox = player.daggerHitbox; 


            for(int i = 0; i < entities.length; i++){
                if(entities[i] != null){
                    ganar = false;
                    collisionChecker.checkDaggerHitbox(daggerHitbox, entities[i]);
                    entities[i].update();

                    if(entities[i].life <=0){
                        entities[i] = null;
                    }
                }                
            }
            if(ganar){
                gameState = winState;
            }

            player.update();
            mapM.update();
        }

        if(gameState == winState){

        }
    }
    
    //dibujar
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;


        if(gameState == winState){
        }

        if(gameState == titleState){
        }else{

            mapM.draw(g2);

            for(int i = 0; i < entities.length; i++){
                if(entities[i] != null){
                    entities[i].draw(g2);
                }
            }

            player.draw(g2);
            //player.drawDagger(g2);
        }
        ui.draw(g2);

        g2.dispose();
    }

    //Musica
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(int i){
        sound.stop();
    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        player.updateAngle(e.getX(), e.getY());
        player.updateMousePosition(e.getX(), e.getY());
        //repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }    
}
