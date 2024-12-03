package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player extends Entity{
    int mouseX, mouseY;
    int playerRadius = gp.tileSize/2;
    int daggerRadius = 20;

    KeyHandler keyH;
    BufferedImage image = null, dagger = null;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getDaggerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = " ";
        maxLife = 5;
        life = 4;
    }

    public void getPlayerImage(){
        try {
            for(int i = 0; i < frames; i++){
                idle[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_idle_" + i + ".png"));
                up[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_up_" + i + ".png"));
                down[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_down_" + i  + ".png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_left_" + i + ".png"));
                right[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_right_" + i + ".png"));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDaggerImage(){
        try{
            dagger = ImageIO.read(getClass().getResourceAsStream("/res/weapons/dagger.png"));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void contactMonster(){
        life -= 1;
    }

    public void updateMousePosition(int x, int y){
        this.mouseX = x;
        this.mouseY = y;
    }

    public void update(){
        //System.out.println(direction);

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            

            if(keyH.upPressed == true){
                direction = "up";
            }
            if(keyH.downPressed == true){
                direction = "down";
            }
            if(keyH.leftPressed == true){
                direction = "left";
            }
            if(keyH.rightPressed == true){
                direction = "right";
            }
            System.out.println(direction);

            //colisiones
            collisionOn = false;
            gp.collisionChecker.checkTileCollision(this);

            for(int i = 0; i < gp.entities.length; i++){
                if(gp.entities[i] != null){
                    gp.collisionChecker.checkEntityCollision(this, gp.entities[i]);
                    if(collisionOn){
                        //contactMonster();
                        break;
                    }
                }
            }
        //    gp.eHandler.checkEvent();
            if(!collisionOn){

                switch(direction){
                    case "up":
                        y -= speed;   
                        break;
    
                    case "down":
                        y += speed;   
                        break;
    
                    case "left":
                        x -= speed;   
                        break;
                        
                    case "right":
                        x += speed;   
                        break;
    
                    default:
                        break;
                }
            }            
        }

        spriteCounter++;
        
        int frameLimit = direction.equals(" ") || direction.equals("up")? 3 : 2;

        if(spriteCounter > 20){
            spriteNum++;
            if(spriteNum >= frameLimit){
                spriteNum = 0;
            }
            spriteCounter = 0;
        }
        //System.out.println("valor x: " + x + "  valor y: " + y);
    }

    public void draw(Graphics2D g2){
        switch(direction){
            case "up":
                image = up[spriteNum];
                break;
    
            case "down":
                image = down[spriteNum];
                break;
    
            case "left":
                image = left[spriteNum];
                break;
    
            case "right":
                image = right[spriteNum];
                break;
    
            default:
                image = idle[spriteNum];
                break;
        }
                
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        drawDagger(g2);
    
    }

    public void drawDagger(Graphics2D g2){
        // Calculate the player's center
        int centerX = x + gp.tileSize / 2;
        int centerY = y + gp.tileSize / 2;
    
        // Calculate the angle to the mouse
        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
    
        // Define the radius of the player's "circle"
        int playerRadius = gp.tileSize / 2; // The player's "circle" radius
    
        // Adjust the dagger's position so its TIP starts outside the circle
        int daggerX = centerX + (int) ((playerRadius + 16) * Math.cos(angle)); // Add the distance from the center to the dagger's tip (16 px)
        int daggerY = centerY + (int) ((playerRadius + 16) * Math.sin(angle));
    
        // Offset the sprite so its tip aligns with the calculated dagger position
        int spriteX = daggerX; // Center of sprite is 16px left of the tip
        int spriteY = daggerY;      // Sprite's top edge aligns with the tip
    
        // Rotate and draw the dagger
        Graphics2D g2Rotated = (Graphics2D) g2.create(); // Create a copy for rotation
        g2Rotated.rotate(angle, daggerX, daggerY);       // Rotate around the dagger's tip
        g2Rotated.drawImage(dagger, spriteX, spriteY, null); // Draw the dagger
        g2Rotated.dispose(); // Dispose the rotated Graphics2D to avoid interference
    }
    



/* 
    public void drawDagger(Graphics2D g2) {
        if(dagger == null) return;

        // Calculate player center
        int centerX = x + gp.tileSize / 2;
        int centerY = y + gp.tileSize / 2;

        // Calculate angle to the mouse
        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);

        int daggerX = centerX + (int) (playerRadius * Math.cos(angle)) - (dagger.getWidth() / 2);
        int daggerY = centerY + (int) (playerRadius * Math.sin(angle)) - (dagger.getHeight() / 2);

        AffineTransform transform = new AffineTransform();
        transform.translate(daggerX, daggerY);

    //    int daggerWidth = dagger.getWidth()/2/2;
    //    int daggerHeight = dagger.getHeight()/2/2;
        
    //    transform.rotate(angle, daggerWidth/2, daggerHeight/2);

        Graphics2D g2Rotated = (Graphics2D) g2.create();

        g2Rotated.rotate(angle, centerX, centerY);       // Rotate around the player's center
        g2Rotated.drawImage(dagger, daggerX, daggerY, null); // Draw the dagger
        g2Rotated.dispose(); // Dispose the rotated Graphics2D to prevent interference


        // Calculate the dagger's tip position
        //int daggerTipX = centerX + (int) ((playerRadius + daggerRadius) * Math.cos(angle));
        //int daggerTipY = centerY + (int) ((playerRadius + daggerRadius) * Math.sin(angle));

        // Draw the dagger as a line
        //g2.drawImage(dagger, transform, null);
        //g2.setColor(Color.RED);
        //g2.drawLine(daggerStartX, daggerStartY, daggerTipX, daggerTipY);
    }
*/


}
