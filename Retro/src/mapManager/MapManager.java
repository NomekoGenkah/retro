package mapManager;

import main.GamePanel;
import entity.Player;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapManager {
    // Game system components
    GamePanel gp;
    public TileManager tileM;
    Player player;

    // Map and screen variables
    int mapScreenNum[][];
    int x, y;
    final int mapSize = 3;

    // Edge buffer for smoother map transitions
    final int edgeBuffer = 3;

    public MapManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        tileM = new TileManager(gp);
        mapScreenNum = new int[mapSize][mapSize];
        x = 0; 
        y = 0;

        loadMap("/res/maps/map0.txt");
        tileM.loadMap("/res/screens/screen" + mapScreenNum[0][0] + ".txt");
    }

    public void loadMap(String mapPath) {
        try {
            InputStream iS = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(iS));

            int col = 0;
            int row = 0;

            while (col < mapSize && row < mapSize) {
                String line = br.readLine();

                while (col < mapSize) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapScreenNum[col][row] = num;
                    col++;
                }

                if (col == mapSize) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeMap(String direction) {
        switch (direction) {
            case "up":
                if (withinBorder(x, y - 1)) {
                    y--;
                    player.y = gp.screenHeight - gp.tileSize - edgeBuffer;
                }
                break;
            case "down":
                if (withinBorder(x, y + 1)) {
                    y++;
                    player.y = edgeBuffer;
                }
                break;
            case "left":
                if (withinBorder(x - 1, y)) {
                    x--;
                    player.x = gp.screenWidth - gp.tileSize - edgeBuffer;
                }
                break;
            case "right":
                if (withinBorder(x + 1, y)) {
                    x++;
                    player.x = edgeBuffer;
                }
                break;
            default:
                break;
        }

        // Load the map for the new screen
        tileM.loadMap("/res/screens/screen" + mapScreenNum[x][y] + ".txt");
    }

    public boolean withinBorder(int nextX, int nextY) {
        return nextX >= 0 && nextX < mapSize && nextY >= 0 && nextY < mapSize && mapScreenNum[nextX][nextY] != -1;
    }

    public void update() {
        // Determine if the player has moved to the screen edge
        String direction = null;

        if (player.y <= edgeBuffer) {
            direction = "up";
        } else if (player.y >= gp.screenHeight - gp.tileSize - edgeBuffer) {
            direction = "down";
        } else if (player.x <= edgeBuffer) {
            direction = "left";
        } else if (player.x >= gp.screenWidth - gp.tileSize - edgeBuffer) {
            direction = "right";
        }

        // Change map if the player has reached the edge
        if (direction != null) {
            changeMap(direction);
        }
    }

    public void draw(Graphics2D g2) {
        tileM.draw(g2);
    }
}
