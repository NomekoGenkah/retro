package retro;

import monster.MON_GreenSlime;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

    }

    public void setGreenSlime(){
        gp.greenSlime[0] = new MON_GreenSlime(gp);
        gp.greenSlime[0].x = gp.tileSize * 3;
        gp.greenSlime[0].y = gp.tileSize * 3;
    }
    
}
