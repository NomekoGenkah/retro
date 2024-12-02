package entity;

import javax.imageio.ImageIO;

import main.GamePanel;

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
    
}
