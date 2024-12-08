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
    double angle;
    public Rectangle daggerHitbox;
    public boolean isAttacking = false;
    private int attackCounter = 0;
    private final int attackDuration = 15;

    int mouseX, mouseY;
    int playerRadius = gp.tileSize/2;
    int daggerRadius = 20;

    public int playerState;
    final int playState = 0;
    public final int deathState = 1;

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

        daggerHitbox = new Rectangle(0, 0, 32, 32);

        setDefaultValues();
        getPlayerImage();
        getDaggerImage();
    }

    public void setDefaultValues(){
        playerState = playState;
        x = 100;
        y = 100;
        speed = 4;
        direction = " ";
        maxLife = 5;
        life = 2;
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

    public void attack(){
        if(!isAttacking){
            isAttacking = true;
            attackCounter = 0;
        }
    }

    public void updateMousePosition(int x, int y){
        this.mouseX = x;
        this.mouseY = y;
    }

    public void updateAngle(int mouseX, int mouseY) {
        int playerCenterX = x + gp.tileSize / 2;
        int playerCenterY = y + gp.tileSize / 2;
    
        angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
    }

    public void onDeath(){
        playerState = deathState;

    }

    public void update(){
        if(life <= 0){
            playerState = deathState;
        }
        if(playerState == deathState){
            
        }

        if(isAttacking){
            attackCounter++;

            int daggerHitboxX = gp.player.x + gp.player.solidArea.width  + (int) (gp.player.solidArea.width  * Math.cos(angle)*2);
            int daggerHitboxY = gp.player.y + gp.player.solidArea.height  + (int) (gp.player.solidArea.height  * Math.sin(angle)*2);
            daggerHitbox.setBounds(daggerHitboxX - 16, daggerHitboxY - 16, 32, 32);

            if(attackCounter >= attackDuration){
                isAttacking = false;
                daggerHitbox.setBounds(0, 0, 0, 0);
            }
        }

        if(playerState == playState){

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

                for(int i = 0; i < gp.entities.length; i++){
                    if(gp.entities[i] != null){
                        gp.collisionChecker.checkEntityCollision(this, gp.entities[i]);
                        if(collisionOn){
                            contactMonster();
                            break;
                        }
                    }
                }

                gp.collisionChecker.checkTileCollision(this);
    

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
    }

    public void draw(Graphics2D g2){

        //debug
        if(isAttacking){
            g2.setColor(Color.RED);
            g2.fillRect(daggerHitbox.x, daggerHitbox.y, daggerHitbox.width, daggerHitbox.height);
        }


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
        int centerX = x + gp.tileSize / 2;
        int centerY = y + gp.tileSize / 2;
    
        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
    
        int playerRadius = gp.tileSize / 2; 
    
        int daggerX = centerX + (int) ((playerRadius + 8) * Math.cos(angle)); 
        int daggerY = centerY + (int) ((playerRadius + 8) * Math.sin(angle));
    
        
        int spriteX = daggerX; 
        int spriteY = daggerY;      
    
        // rotar y dibujar daga
        Graphics2D g2Rotated = (Graphics2D) g2.create(); 
        g2Rotated.rotate(angle, daggerX, daggerY);     
        g2Rotated.drawImage(dagger, spriteX, spriteY, null); 
        g2Rotated.dispose(); 
    }

}
