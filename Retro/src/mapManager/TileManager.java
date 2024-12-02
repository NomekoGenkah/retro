package mapManager;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    private String tileName[] = {"grass", "water", "sand", "wall", "earth"};

    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImages();
    }

    //cargar las tiles desde la carpeta de tiles
    public void getTileImages(){
        try {
            for(int i = 0; i < tileName.length; i++){
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + tileName[i] + ".png"));
                tile[i].collision = false;
            }
            tile[3].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath){

        try {
            InputStream iS = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(iS));

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){

                String line = br.readLine();

                while(col < gp.maxScreenCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num / 10;
                    col++;

                }

                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTileSolid(int col, int row) {
        if (col < 0 || col >= gp.maxScreenCol || row < 0 || row >= gp.maxScreenRow) {
            return true; // Treat out-of-bounds tiles as solid
        }

        // Get the tile number at the specified position
        int tileNum = mapTileNum[col][row];

        // Check the tile's collision property
        return tile[tileNum].collision;
    }
    
    public void draw(Graphics2D g2){

        int col = 0, row = 0, x = 0, y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow){
            
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
