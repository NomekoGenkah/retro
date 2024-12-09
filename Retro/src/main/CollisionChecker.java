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
                if(!gp.player.invulnerable){
                    gp.player.takeDamage();
                }
                //entity.life--;
                //gp.gameState = gp.pauseState;
            }
        }
    }

    public void checkDaggerHitbox(Rectangle daggerHitbox, Entity target) {
        if (daggerHitbox == null || target == null) return;
    
        // Crear un rectángulo para el área sólida del objetivo
        Rectangle targetSolidArea = new Rectangle(
                target.x + target.solidArea.x,
                target.y + target.solidArea.y,
                target.solidArea.width,
                target.solidArea.height
        );
    
        // Verificar si el hitbox de la daga intersecta con el área sólida del objetivo
        if (daggerHitbox.intersects(targetSolidArea)) {
            // Lógica de colisión (por ejemplo, reducir la vida del objetivo)
            target.takeDamage(); // Ejemplo: Reducir vida del objetivo
            System.out.println("Dagger hit target: " + target);
        }
    }
    
    
}
