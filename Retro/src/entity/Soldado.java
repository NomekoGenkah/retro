package entity;

import java.util.Random;

import javax.imageio.ImageIO;
import main.GamePanel;

public class Soldado extends Entity {
    public boolean furiainmortal = false;
    public int duracion = 0;
    public final int duracionMaxima = 480;

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
        if(!furiainmortal){
            this.life--;
        }
        if(this.life == 1){
            furiainmortal = true; // R trynda
        }
        this.setAction();
    }

    @Override
    public void setAction() {
        this.actionLockCounter++;
    
        if (this.actionLockCounter >= 10) {
            if (furiainmortal) {

                int deltaX = gp.player.x - this.x;
                int deltaY = gp.player.y - this.y;
    
                if (Math.abs(deltaX) > Math.abs(deltaY)) {

                    if (deltaX > 0) {
                        direction = "right";
                    } else {
                        direction = "left";
                    }
                } else {

                    if (deltaY > 0) {
                        direction = "down";
                    } else {
                        direction = "up";
                    }
                }
            } else {

                Random random = new Random();
                int i = random.nextInt(100) + 1;
    
                if (i <= 25) {
                    this.direction = "up";
                } else if (i > 25 && i <= 50) {
                    this.direction = "down";
                } else if (i > 50 && i <= 75) {
                    this.direction = "left";
                } else if (i > 75 && i <= 100) {
                    this.direction = "right";
                }
            }
    
            this.actionLockCounter = 0; // Reinicia el contador de acciones
        }
    }
    

    @Override
    public void update() {
        super.update();

        if (furiainmortal) {
            this.speed = 3;
            if(duracion > duracionMaxima/2){
                this.speed = 4;
            }
            //this.life= ;
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
                idle[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_down_.png"));
                up[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_up_.png"));
                down[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_down_.png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_left_.png"));
                right[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/soldado_right_.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cargarEspecial(){
        try{
            especial = ImageIO.read(getClass().getResourceAsStream("/res/monster/boss3.png"));
            
        }catch(Exception e){
            // TODO: handle exception
        }
    }
}
