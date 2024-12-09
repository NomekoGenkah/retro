package main;

import java.io.*;

public class SaveManager {
    GamePanel gp;

    public SaveManager(GamePanel gp) {
        this.gp = gp;
    }

    // Save game data to a file
    public void saveGame() {
        try {
            File saveFile = new File("savegame.dat");
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Save player data
            oos.writeInt(gp.player.x); // Player's X position
            oos.writeInt(gp.player.y); // Player's Y position
            oos.writeInt(gp.player.life); // Player's current health

            // Save map or game state if needed
            oos.writeInt(gp.mapM.x); // Current map X position
            oos.writeInt(gp.mapM.y); // Current map Y position

            // Add any other necessary fields...

            oos.close();
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save the game.");
            e.printStackTrace();
        }
    }

    // Load game data from a file
    public void loadGame() {
        try {
            File saveFile = new File("savegame.dat");
            if (!saveFile.exists()) {
                System.out.println("No save file found.");
                return;
            }

            FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Load player data
            gp.player.x = ois.readInt(); // Player's X position
            gp.player.y = ois.readInt(); // Player's Y position
            gp.player.life = ois.readInt(); // Player's current health

            // Load map or game state if needed
            gp.mapM.x = ois.readInt(); // Current map X position
            gp.mapM.y = ois.readInt(); // Current map Y position

            // Add any other necessary fields...

            ois.close();
            System.out.println("Game loaded successfully.");
        } catch (IOException e) {
            System.err.println("Failed to load the game.");
            e.printStackTrace();
        }
    }
}
