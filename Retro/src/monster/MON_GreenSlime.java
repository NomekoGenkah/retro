package monster;

import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import retro.GamePanel;

public class MON_GreenSlime extends Entity{

    public MON_GreenSlime(GamePanel gp){
        super(gp);

        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        direction = "down";

        getImage();
    }

    public void getImage(){

        try {
            for (int i = 0; i < down.length; i++) {
                idle[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/greenslime_down_" + i + ".png"));
                up[i] = idle[i]; down[i] = idle[i]; left[i] = idle[i]; right[i] = idle[i];
            }    
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

        /* 

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100) + 1;
    
            if(i <= 25){
                direction = "up";
            }
    
            if(i > 25 && i <= 50){
                direction = "down";            
            }
    
            if(i > 50 && i <= 75){
                direction = "left";
            }
    
            if(i > 75 && i <= 100){
                direction = "right";
            }

            actionLockCounter = 0;

        }
    }
                    */




}