package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    public int x;
    public int y;
    public int speed;

    public final int frames = 3;

    public BufferedImage[] up = new BufferedImage[frames], down = new BufferedImage[frames], left = new BufferedImage[frames], right = new BufferedImage[frames];
    public BufferedImage[] idle = new BufferedImage[frames]; 
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;

    public boolean collisionOn = false;

    public int maxLife;
    public int life;

    
}
