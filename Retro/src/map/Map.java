package map;

import entity.Player;
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
    Player player;
    public TileMaganer tileMaganer;
    int mapScreenNum[][];
    int x, y;

    public Map(GamePanel gp, Player player){
        this.gp = gp;
        this.player = player;
        tileMaganer = new TileMaganer(gp);
        mapScreenNum = new int[3][3];
        x = 0; y = 0;

        loadMap("/res/maps/map0.txt");
        tileMaganer.loadMap("/res/screens/screen" + mapScreenNum[0][0] + ".txt");
    }

    public void changeMap(String direction){
        switch (direction) {
            case "up":
                if(withinBorder(mapScreenNum, x, y-1)){
                    y--;
                    player.y = gp.screenHeight - 4;
                }
                break;
            case "down":
                if(withinBorder(mapScreenNum, x, y+1)){
                    y++;
                    player.y = 4;
                }
                break;
            case "left":
                if(withinBorder(mapScreenNum, x-1, y)){
                    x--;
                    player.x = gp.screenWidth - 4;
                }
                break;
            case "right":
                if(withinBorder(mapScreenNum, x+1, y)){
                    x++;
                    player.x = 4;
                }
                break;
            default:
                break;
        }

        tileMaganer.loadMap("/res/screens/screen" + mapScreenNum[x][y] + ".txt");
        //tileMaganer.loadMap("/res/screens/screen" + mapScreenNum[x][y] + ".txt");
    }

    public boolean withinBorder(int[][] matrix, int x, int y){

        return x >= 0 && x < matrix.length && y >= 0 && y < matrix.length && matrix[x][y] != -1;
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

    public void update(int screenWidth, int screenHeight, int x, int y){
        String direction = " ";
        if(y == 0){
            direction = "up";
        }else if(y == screenHeight){
            direction = "down";
        }else if(x == 0){
            direction = "left";
        }else if(x == screenWidth){
            direction = "right";
        }
        if(direction != " "){
            changeMap(direction);
        }
        //changeMap(direction);
    }

    public void draw(Graphics2D g2){
        tileMaganer.draw(g2);
    }
}
