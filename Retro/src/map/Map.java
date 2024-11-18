package map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;

import retro.GamePanel;
import tile.TileMaganer;

public class Map {

    GamePanel gp;
    TileMaganer tileMaganer;
    int mapScreenNum[][];

    public Map(GamePanel gp){
        this.gp = gp;
        tileMaganer = new TileMaganer(gp);
        mapScreenNum = new int[3][3];

        loadMap("/res/maps/map0.txt");
        tileMaganer.loadMap("/res/screens/screen" + mapScreenNum[0][0] + ".txt");
    }

    public void loadMap(String mapPath){
        int mapSize = 3;

        try {
            InputStream iS = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(iS));
        //    System.out.println(br.readLine());

            int col = 0;
            int row = 0;

            while(col < mapSize && row < mapSize){

                String line = br.readLine();

                while(col < mapSize){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapScreenNum[col][row] = num;
                    col++;
                }

                if(col == mapSize){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        tileMaganer.draw(g2);
    }
}
