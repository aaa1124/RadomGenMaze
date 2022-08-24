package byow.proj3;

import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

public class Helper {

    int seed;
    Random rand;

    int worldWidth;
    int worldHeight;


    int maxRoomH;
    int maxRoomW;

    public Helper() {
        seed = 0;
        rand = new Random();
    }

    public Helper(int worldWidth, int worldHeight) {
        seed = 0;
        rand = new Random();

        this.worldHeight = worldHeight;

        this.worldWidth = worldWidth;
        this.maxRoomH = maxHandWrooms(worldWidth, worldHeight).get("maxH");
        this.maxRoomW = maxHandWrooms(worldWidth, worldHeight).get("maxW");

    }

    public Helper(int worldWidth, int worldHeight, int randomSeed) {
        this.seed = randomSeed;

        rand = new Random(this.seed);
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
        this.maxRoomH = maxHandWrooms(worldWidth, worldHeight).get("maxH");
        this.maxRoomW = maxHandWrooms(worldWidth, worldHeight).get("maxW");


    }







    public TETile[][] worldNOTHING(int width, int height) {

        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }









    public static int calcuEndPoint(Integer heOrWi, Integer centralXorY, String direction) {

        switch (direction) {
            case "bot": case "left":
                if (heOrWi % 2 == 0) {

                    return centralXorY - (heOrWi / 2);
                } else {
                    return centralXorY - (heOrWi / 2) - 1;
                }

            case "top": case "right":
                return centralXorY + (heOrWi / 2) - 1;
            default:
                break;


        }
        return -999;
    }




    public static HashMap<String, Integer> maxHandWrooms(int worldWidth, int  worldHeight) {
        HashMap<String, Integer> temp1 = new HashMap<>();

        double ratio = (double) worldHeight / (double) worldWidth;

        Integer maxRoomSize = worldWidth * worldHeight / 11;


        double maxW = Math.sqrt(maxRoomSize / ratio);

        double maxH = maxW * ratio;

        temp1.put("maxH", (int) maxH);
        temp1.put("maxW", (int) maxW);
        return temp1;

    }


    public static void main(String[] args) {
        Random t1 = new Random(1);

        System.out.println(t1.nextInt(100));
        System.out.println(t1.nextInt(100));
        System.out.println(t1.nextInt());

        double ratioHW = RandomUtils.uniform(t1, 0.5, 2.0);

        System.out.println(ratioHW);


    }

}
