package main;

import entity.MON_GreenSlime;

public class AssetSetter {

        GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

    }

    public void setGreenSlime(){
        
        gp.entities[0] = new MON_GreenSlime(gp);
        gp.entities[0].x = gp.tileSize * 3;
        gp.entities[0].y = gp.tileSize * 3;
    }
    
}
