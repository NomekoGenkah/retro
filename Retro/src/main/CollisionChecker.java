package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        if(!checkBounds()){
            return;
        }

        int entityLeftScreenX = entity.x + entity.solidArea.x;
        int entityRightScreenX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopScreenY = entity.y + entity.solidArea.y;
        int entityBottomScreenY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftScreenX/gp.tileSize;
        int entityRightCol = entityRightScreenX/gp.tileSize;
        int entityTopRow = entityTopScreenY/gp.tileSize;
        int entityBottomRow = entityBottomScreenY/gp.tileSize;

        int tileNum1 = 0, tileNum2 = 0;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopScreenY - entity.speed)/gp.tileSize;
                tileNum1 = gp.map.tileMaganer.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.map.tileMaganer.mapTileNum[entityRightCol][entityTopRow];
                break;
            
            case "down":
                entityBottomRow = (entityBottomScreenY + entity.speed)/gp.tileSize;
                tileNum1 = gp.map.tileMaganer.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.map.tileMaganer.mapTileNum[entityRightCol][entityBottomRow];
                break;

            case "left":
                entityLeftCol = (entityLeftScreenX - entity.speed)/gp.tileSize;
                tileNum1 = gp.map.tileMaganer.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.map.tileMaganer.mapTileNum[entityLeftCol][entityBottomRow];
                break;

            case "right":
                entityRightCol = (entityRightScreenX + entity.speed)/gp.tileSize;
                tileNum1 = gp.map.tileMaganer.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.map.tileMaganer.mapTileNum[entityRightCol][entityBottomRow];
                break;
        
            default:
                break;
        }

        if(gp.map.tileMaganer.tile[tileNum1].collision || gp.map.tileMaganer.tile[tileNum2].collision){
            System.out.println("TILE 1 COLISION: " + gp.map.tileMaganer.tile[tileNum1].collision + "   TILE 2 COLISION: " + gp.map.tileMaganer.tile[tileNum2].collision);
            entity.collisionOn = true;
        }
        
    }

    public boolean checkBounds(){
        return gp.player.x >= 0 && gp.player.x < gp.screenWidth - (gp.screenWidth/15) && gp.player.y >= 0 && gp.player.y < gp.screenHeight - (gp.screenHeight/11);
    }

    //monster npc
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        //or(int i = 0; i < gp; i++){

            
        //}


        return 0;
    }

}
