package entity;

import javax.imageio.ImageIO;

import main.GamePanel;

public class MON_GreenSlime extends Entity{
    public boolean drogas = false;
    public int duracion = 0;
    public final int duracionMaxima = 150;

    public MON_GreenSlime(GamePanel gp){
        super(gp);

        name = "Green Slime";
        speed = 1;
        maxLife = 40;
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

    @Override
    public void takeDamage(){
        this.life--;
    //    this.speed = this.speed + 1;
        drogas = true;
        this.setAction();
    }

    @Override
    public void update(){
        super.update();

        if(drogas){
            this.speed = 20;
            duracion++;
            if(duracion >= duracionMaxima){
                duracion = 0;
                drogas = false;
            }
        }else{
            this.speed = 1;
        }
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
