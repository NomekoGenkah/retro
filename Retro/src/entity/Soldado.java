package entity;

import javax.imageio.ImageIO;
import main.GamePanel;

public class Soldado extends Entity {
    public boolean furiainmortal = false;
    public int duracion = 0;
    public final int duracionMaxima = 150;

    public Soldado(GamePanel gp) {
        super(gp);

        name = "Soldado Rojo";
        speed = 2; 
        maxLife = 10; 
        life = maxLife;
        solidArea.x = 5;
        solidArea.y = 15;
        solidArea.width = 40;
        solidArea.height = 50;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        direction = "down";

        getImage();
    }

    @Override
    public void takeDamage() {
        this.life--;
        furiainmortal = true; // R trynda
        this.setAction();
    }

    @Override
    public void update() {
        super.update();

        if (furiainmortal) {
            this.speed = 15;
            this.maxLife= 50;
            duracion++;
            if (duracion >= duracionMaxima) {
                duracion = 0;
                furiainmortal = false;
            }
        } else {
            this.speed = 2; 
        }
    }

    public void getImage() {
        try {
            for (int i = 0; i < down.length; i++) {
                idle[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_down_" + i + ".png"));
                up[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_up_" + i + ".png"));
                down[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_down_" + i + ".png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_left_" + i + ".png"));
                right[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_right_" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
