package main;
import java.awt.Rectangle;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;

    }

    public void checkTileCollision(Entity entity){
        // Calculate the entity's current and future tile positions
        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.y + entity.solidArea.y;
        int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;
    
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
    
        int nextLeftCol, nextRightCol, nextTopRow, nextBottomRow;
    
        // Determine the future position based on the direction
        switch (entity.direction) {
            case "up":
                nextTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (gp.mapM.tileM.isTileSolid(entityLeftCol, nextTopRow) || 
                    gp.mapM.tileM.isTileSolid(entityRightCol, nextTopRow)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                nextBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (gp.mapM.tileM.isTileSolid(entityLeftCol, nextBottomRow) || 
                    gp.mapM.tileM.isTileSolid(entityRightCol, nextBottomRow)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                nextLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (gp.mapM.tileM.isTileSolid(nextLeftCol, entityTopRow) || 
                    gp.mapM.tileM.isTileSolid(nextLeftCol, entityBottomRow)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                nextRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (gp.mapM.tileM.isTileSolid(nextRightCol, entityTopRow) || 
                    gp.mapM.tileM.isTileSolid(nextRightCol, entityBottomRow)) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public void checkEntityCollision(Entity entity, Entity target){
        // Reset collision flag for the entity
        //entity.collisionOn = false;
    
        // Calculate the entity's current position
    //    int entityLeftWorldX = entity.x + entity.solidArea.x;
    //    int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
    //    int entityTopWorldY = entity.y + entity.solidArea.y;
    //    int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;
    
        // Calculate the entity's future position based on its direction
        int entityNextX = entity.x;
        int entityNextY = entity.y;
        switch (entity.direction) {
            case "up":
                entityNextY -= entity.speed;
                break;
            case "down":
                entityNextY += entity.speed;
                break;
            case "left":
                entityNextX -= entity.speed;
                break;
            case "right":
                entityNextX += entity.speed;
                break;
        }
    
        // Create a rectangle for the entity's future solid area
        Rectangle entityFutureSolidArea = new Rectangle(
                entityNextX + entity.solidArea.x,
                entityNextY + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );
    
        // Check collision with the target
        if (target != null) {
            Rectangle targetSolidArea = new Rectangle(
                    target.x + target.solidArea.x,
                    target.y + target.solidArea.y,
                    target.solidArea.width,
                    target.solidArea.height
            );
    
            // Check for intersection
            if (entityFutureSolidArea.intersects(targetSolidArea)){
                entity.collisionOn = true;
                entity.life--;
                gp.gameState = gp.pauseState;
            }
        }
    }
    




/* 
    public void checkEntityCollision(Entity entity, Entity target) {
        // Reset collisionOn for the entity
        entity.collisionOn = false;

        // Calculate the entity's solid area
        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.y + entity.solidArea.y;
        int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;

        if (target != null && target != entity) { // Ignore null and self-collision
            // Calculate the target's solid area
            int targetLeftWorldX = target.x + target.solidArea.x;
            int targetRightWorldX = target.x + target.solidArea.x + target.solidArea.width;
            int targetTopWorldY = target.y + target.solidArea.y;
            int targetBottomWorldY = target.y + target.solidArea.y + target.solidArea.height;

            // Check for collision
            if (entityLeftWorldX < targetRightWorldX &&
                    entityRightWorldX > targetLeftWorldX &&
                    entityTopWorldY < targetBottomWorldY &&
                    entityBottomWorldY > targetTopWorldY) {
                entity.collisionOn = true;
                target.collisionOn = true;
                gp.player.contactMonster();
                // Stop checking after detecting one collision
            }
        }
    }
        */
    



/* 
    private int getDirectionOffsetX(String direction) {
        return switch (direction) {
            case "left" -> -1;
            case "right" -> 1;
            default -> 0;
        };
    }

    private int getDirectionOffsetY(String direction) {
        return switch (direction) {
            case "up" -> -1;
            case "down" -> 1;
            default -> 0;
        };
    }
*/




    
}
