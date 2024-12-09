package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_HealthBar;
import object.Object;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;

    BufferedImage heartImage;

    public boolean messageOn = false;
    public String message = "";

    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        Object heart = new OBJ_HealthBar(gp);
        heartImage = heart.image;

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);


        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        if(gp.gameState == gp.playState){
            drawPlayerLife();
            //

        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        if(gp.gameState == gp.winState){
            drawWinScreen();
        }

        if(gp.player.playerState == gp.player.deathState){
            drawDeathScreen();
        }
    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;

        g2.setColor(Color.RED);
        g2.fillRect(x+x, y+ y/2, x*gp.player.life*2, y); //vida faltan variables
        g2.setColor(Color.BLACK);
        g2.drawRect(x+x, y+ y/2, x*gp.player.maxLife*2, y); //borde faltan variables

        g2.drawImage(heartImage, x, y, gp.tileSize, gp.tileSize,  null); //heart
    }

    public void drawTitleScreen(){

        //title
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 48F));
        String text = "Domeyko \nAssassins";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        //shadow
        g2.setColor(Color.PINK);
        g2.drawString(text, x+1, y+1);

        //title
        g2.setColor(Color.RED);
        g2.drawString(text, x, y);

        //menu
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 24f));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x - gp.tileSize, y);
        }
    }
    
    public void drawPauseScreen(){

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(gp.screenWidth / 3 - 10, gp.screenHeight / 5 - 10, gp.screenWidth / 3 + 20, gp.screenHeight/2+20);

        g2.setColor(Color.gray);
        g2.fillRect(gp.screenWidth/3, gp.screenHeight/5, gp.screenWidth/3, gp.screenHeight/2);

        g2.setColor(Color.black);

        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 34f));
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight/4;

        g2.drawString(text, x, y);

        text = "CONTINUE";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "OPTIONS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    public void drawWinScreen(){
        String text = "¡GANASTE!";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

    }

    public void drawDeathScreen(){
        String text = "DEAD";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        
        return gp.screenWidth/2 - length/2;
    }
}
