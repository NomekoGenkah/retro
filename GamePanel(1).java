package main;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import mapManager.MapManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

    final int FPS = 60;

    //ESTADOS DE JUEGO
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    //system
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Sound sound = new Sound();
    int currentScreen = 1;  
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    AssetSetter aSetter = new AssetSetter(this);
    SaveManager saveM = new SaveManager(this);
    //player y entidades
    public Player player = new Player(this, keyH);
    public Entity[] entities = new Entity[3];

    //Mapa
    MapManager mapM = new MapManager(this, player);

    //UI
    UI ui = new UI(this);
    
    private boolean musicChanged = false;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.addMouseMotionListener(this);
        sound.playTrack(0);
    }

    public void setupGame(){
        gameState = titleState;
        aSetter.setGreenSlime();
        //playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void musichangeScreen(int screen) { 
        sound.stop();
        
        if (screen == 0) {
          sound.setFile(1); 
          sound.loop();
        }else if(screen == 1){
          sound.setFile(2);
          sound.loop();
        }
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
        
        if (gameState == titleState) {
            // La música se asegura de que se reproduce en la pantalla de título
            if (!musicChanged) {
                musichangeScreen(0);
                musicChanged = true;
            }

            // Aquí controlas que el juego solo cambie de estado si el usuario elige NEW GAME
            if (ui.commandNum == 0 && keyH.enterPressed) {
                gameState = playState;
                musichangeScreen(1);
                musicChanged = false;
                keyH.enterPressed = false; // Resetea la acción
            }
        }

        if (gameState == playState) {
            player.update();

            for (int i = 0; i < entities.length; i++) {
                if (entities[i] != null) {
                    entities[i].update();
                }
            }
            mapM.update();
        }
    }
    
    //dibujar
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if(gameState == titleState){
            ui.drawTitleScreen(g2);
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

    @Override
    public void mouseMoved(MouseEvent e){
        player.updateMousePosition(e.getX(), e.getY());
        //repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Not used but required for the interface
    }


    
}
