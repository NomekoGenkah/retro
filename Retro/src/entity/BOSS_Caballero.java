package entity;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class BOSS_Caballero extends Entity {

    private BufferedImage[] idleFrames;    
    private BufferedImage[] attackFrames; 
    private BufferedImage explosionImage;
    private boolean isAttacking = false;   
    private int frameCounter = 0;         
    private final int attackDuration = 60;

    public BOSS_Caballero(GamePanel gp) {
        super(gp);
        loadImages(); 
        setDefaultValues();
    }

    private void setDefaultValues() {
        this.x = 400; 
        this.y = 300;
        this.speed = 1;  
        this.direction = "idle"; 
        this.maxLife = 150;
        this.life = maxLife;

        setDefaultHitBox();
    }

    public void setDefaultHitBox(){
        solidArea.x = 30;
        solidArea.y = 20;
        solidArea.width = 220;
        solidArea.height = 300;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    private void loadImages() {
        try {
            //cargar frames de animación en reposo
            idleFrames = new BufferedImage[3];
            for (int i = 0; i < idleFrames.length; i++) {
                idleFrames[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/caballero_idle_.png"));
            }

            //cargar frames de ataque
            attackFrames = new BufferedImage[3];
            for (int i = 0; i < attackFrames.length; i++) {
                attackFrames[i] = ImageIO.read(getClass().getResourceAsStream("/res/monster/caballero_attack_" + i + ".png"));
            }

            //cargar imagen de explosión
            explosionImage = ImageIO.read(getClass().getResourceAsStream("/res/monster/explosion.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAttack() {
        if (!isAttacking) {
            isAttacking = true;
            frameCounter = 0;
        }
    }

    @Override
    public void setAction() {
        //super.setAction();

        this.actionLockCounter++;

        if(this.actionLockCounter == 80){
            Random random = new Random();
            int i = random.nextInt(100) + 1;
    
            if(i <= 25){
                this.isAttacking = true;
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

    @Override
    public void update() {
        super.update();
        if (isAttacking) {
            frameCounter++;

            if(frameCounter >= 2){
                solidArea.x = -10;
                solidArea.y = -10;
                solidArea.width = 300;
                solidArea.height = 400;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;    
            }
            if (frameCounter >= attackDuration) {
                isAttacking = false;
                frameCounter = 0;
                setDefaultHitBox();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage currentFrame = null;

        if (isAttacking) {
            int index = (frameCounter / (gp.FPS / attackFrames.length)) % attackFrames.length;
            currentFrame = attackFrames[index];
        } else {
            int index = (frameCounter / (gp.FPS / idleFrames.length)) % idleFrames.length;
            currentFrame = idleFrames[index];
        }

        int scale = 2;
        int width = currentFrame.getWidth() * scale;
        int height = currentFrame.getHeight() * scale;

        if(gp.cheat.isCheatCodeActive()){
            g2.drawImage(especial, x - gp.player.worldX + gp.player.screenX, y - gp.player.worldY + gp.player.screenY, width, height, null);
        }else{
            g2.drawImage(currentFrame, x - gp.player.worldX + gp.player.screenX, y - gp.player.worldY + gp.player.screenY, width, height, null);
        }


        if (isAttacking && frameCounter >= attackDuration - (gp.FPS / 4)) {
            g2.drawImage(
                explosionImage,
                x - gp.player.worldX + gp.player.screenX - width / 2,
                y - gp.player.worldY + gp.player.screenY - height / 2,
                width * 2, height * 2, null
            );
        }
    }
}
