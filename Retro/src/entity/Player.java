package entity;

import retro.GamePanel;
import retro.KeyHandler;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{

    KeyHandler keyH;

    BufferedImage image = null;

    public int playerState;
    public final int idleState = 0;
    public final int moveState = 1;

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
    }

    public void setDefaultValues(){

        x = 100;
        y = 100;
        speed = 5;
        direction = " ";
        maxLife = 5;
        life = 5;
    }

    public void getPlayerImage(){
        try {
            for(int i = 0; i < frames; i++){
                idle[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_idle_" + i + ".png"));
                up[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_up_" + i + ".png"));
                down[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_" + i  + ".png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_left_" + i + ".png"));
                right[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/ezio_right_" + i + ".png"));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(){

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

            //colisiones
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //evento
            gp.eHandler.checkEvent();

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

    }
    
}
