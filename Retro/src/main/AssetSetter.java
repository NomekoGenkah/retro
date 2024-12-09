package main;

import entity.BOSS_Caballero;
import entity.MON_GreenSlime;
import entity.Soldado;

public class AssetSetter {

        GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

    }

    public void setGreenSlime(){
        
        gp.entities[0] = new MON_GreenSlime(gp);
        gp.entities[0].x = gp.tileSize * 6;
        gp.entities[0].y = gp.tileSize * 3;
        

        gp.entities[1] = new MON_GreenSlime(gp);
        gp.entities[1].x = gp.tileSize * 4;
        gp.entities[1].y = gp.tileSize * 4;

        gp.entities[2] = new MON_GreenSlime(gp);
        gp.entities[2].x = gp.tileSize * 7;
        gp.entities[2].y = gp.tileSize * 2;

        gp.entities[3] = new MON_GreenSlime(gp);
        gp.entities[3].x = gp.tileSize * 5;
        gp.entities[3].y = gp.tileSize * 2;

        gp.entities[4] = new Soldado(gp);
        gp.entities[4].x = gp.tileSize * 9;
        gp.entities[4].y = gp.tileSize * 2;

        gp.entities[5] = new BOSS_Caballero(gp);
        gp.entities[5].x = gp.tileSize * 9;
        gp.entities[5].y = gp.tileSize * 6;



    }
    
}
