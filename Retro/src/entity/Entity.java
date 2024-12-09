package entity;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Entity {
    GamePanel gp;
    public int x, y;
    public int speed;
    public final int frames = 3;

    public String name;

    public BufferedImage[] up = new BufferedImage[frames], down = new BufferedImage[frames], left = new BufferedImage[frames], right = new BufferedImage[frames];
    public BufferedImage[] idle = new BufferedImage[frames]; 
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    public int maxLife;
    public int life;

    public int actionLockCounter = 0;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

        this.actionLockCounter++;

        if(this.actionLockCounter == 80){
            Random random = new Random();
            int i = random.nextInt(100) + 1;
    
            if(i <= 25){
                this.direction = "up";
            }
    
            if(i > 25 && i <= 50){
                this.direction = "down";            
            }
    
            if(i > 50 && i <= 75){
                this.direction = "left";
            }
    
            if(i > 75 && i <= 100){
                this.direction = "right";
            }

            this.actionLockCounter = 0;

        }

    }

    public void takeDamage(){
        this.life--;
    }

    public void update(){
        setAction();
        collisionOn = false;
        gp.collisionChecker.checkEntityCollision(this, gp.player);
        gp.collisionChecker.checkTileCollision(this);


        if(!collisionOn){
            switch(direction){
                case "up":
                    y -= speed;
                    if(y < 0) y = 0;   
                    break;

                case "down":
                    y += speed;
                    if(y >= gp.screenHeight - gp.tileSize) y = gp.screenHeight - gp.tileSize;   
                    break;

                case "left":
                    x -= speed;   
                    if(x < 0) x = 0;   
                    break;
                    
                case "right":
                    x += speed;   
                    if(x >= gp.screenWidth - gp.tileSize) x = gp.screenWidth - gp.tileSize;   
                    break;

                default:
                    break;
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
        BufferedImage image = null;


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
