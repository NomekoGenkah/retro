package object;

import javax.imageio.ImageIO;

import retro.GamePanel;

public class OBJ_HealthBar extends SuperObject{
    GamePanel gp;

    public OBJ_HealthBar(GamePanel gp){
        this.gp = gp;

        name = "Heart";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/UI/Heart.png"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
    
}
