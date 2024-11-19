package retro;

import gameStateManager.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;

    public boolean messageOn = false;
    public String message = "";

    GameStateManager gameStateManager;


    public UI(GamePanel gp, GameStateManager gameStateManager){
        this.gp = gp;
        this.gameStateManager = gameStateManager;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

    }



    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gameStateManager.isRunning()){
            //

        }
        if(!gameStateManager.isRunning()){
            drawPauseScreen();
        }
    }
    
    public void drawPauseScreen(){
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        
        return gp.screenWidth/2 - length/2;
    }
    
}