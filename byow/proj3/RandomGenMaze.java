package byow.proj3;

import byow.Core.RandomUtils;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;



import java.io.Serializable;

import java.util.ArrayList;

import java.util.Random;

public class Test3 implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;
    private int WIDTH;
    private int HEIGHT;
    private static final int ROOMMAXWIDTH = 6;
    private static final int ROOMMAXHEIGHT = 6;

    private static final int MAXROOMS = 20;
    private static final int MINROOMS = 11;
    TETile[][] test555;
    TETile[][] world;
    Random rand;
    TETile[][] blackFogWorld;
    Pointer avatarPos;

    ArrayList<Rooms> roomsLocation = new ArrayList<>();
    ArrayList<Pointer> blueLst = new ArrayList<>();
    ArrayList<Rooms> availableForRoomLst = new ArrayList<>();
    ArrayList<Rooms> hallwayStartPoint = new ArrayList<>();



    public Test3(TETile[][] world, Random rand, Integer width, Integer height) {
        //this.world = TETile.copyOf(world);
        this.test555 = world;
        this.world = world;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.rand = rand;
        initialize();

    }

    public void initialize() {

        TETile[][] tempWorld = TETile.copyOf(world);

        for (int x = 2; x < WIDTH - 1; x += 6) {
            for (int y = 2; y < HEIGHT - 1; y += 6) {

                if (world[x][y].equals(Tileset.NOTHING)) {


                    if (x + 2 != WIDTH - 1 && y != 2 && x > 2 || y > 2) {

                        availableForRoomLst.add(new Rooms(x, y));
                    }

                    if (y == 2) {

                        hallwayStartPoint.add(new Rooms(x, y));
                    }

                    world[x][y] = Tileset.WATER;

                }



            }

        }

        Integer numOfRooms = RandomUtils.range(rand, MINROOMS, MAXROOMS);
        oraR(numOfRooms);
        Integer randIndex = rand.nextInt(hallwayStartPoint.size() - 1);
        Integer tX = hallwayStartPoint.get(randIndex).X, tY = hallwayStartPoint.get(randIndex).Y;

        buildWallForOnePoint(tX, tY);
        muda(tX, tY);
        world[tX][tY] = Tileset.AVATAR;


        avatarPos = new Pointer(tX, tY);


        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {

                if (world[x][y].equals(Tileset.WATER)) {
                    world[x][y] = Tileset.NOTHING;
                }

            }
        }
    }

    public void muda(Integer targetX, Integer targetY) {
        Pointer randBlue;
        int randomIndex;

        muda1(targetX, targetY);


        if (blueLst.size() > 1) {
            randomIndex = rand.nextInt(blueLst.size() - 1);
            randBlue = blueLst.get(randomIndex);
            muda2(randBlue, randomIndex);
        } else if (blueLst.size() == 1) {
            randomIndex = 0;
            randBlue = blueLst.get(randomIndex);
            muda2(randBlue, randomIndex);
        }







    }


    public void muda1(Integer targetX, Integer targetY) {



        for (int x = -3; x < 4; x += 6) {
            if (targetX + x > 0 && targetX + x < WIDTH - 1) {
                if (world[targetX + x][targetY].equals(Tileset.NOTHING)) {


                    world[targetX + x][targetY] = Tileset.SAND;
                    blueLst.add(new Pointer(targetX + x, targetY, new Integer[] {x, 0}));
                }
            }

        }




        for (int y = -3; y < 4; y += 6) {
            if (targetY + y > 0 && targetY + y < HEIGHT - 1) {
                if (world[targetX][targetY + y].equals(Tileset.NOTHING)) {




                    world[targetX][targetY + y] = Tileset.SAND;



                    blueLst.add(new Pointer(targetX, targetY + y, new Integer[] {0, y}));

                }
            }

        }






    }

    public void muda2(Pointer blue, int indexOfBlueLst) {
        Integer vI = blue.facingVector[0];
        Integer vJ = blue.facingVector[1];
        blueLst.remove(indexOfBlueLst);
        if (vI != 0 && vJ == 0) {
            if (blue.nX + vI >= 0 && blue.nX + vI < WIDTH) {
                if (world[blue.nX + vI][blue.nY + vJ].equals(Tileset.WATER)) {
                    buildWallForOnePoint(blue.nX + vI, blue.nY + vJ);
                    buildWallForOnePoint(blue.nX, blue.nY);
                    for (int i = blue.nX - 3; i <= blue.nX + 3; i++) {
                        world[i][blue.nY] =  Tileset.FLOOR;
                    }
                    muda(blue.nX + vI, blue.nY + vJ);
                } else if (world[blue.nX + vI][blue.nY + vJ].equals(Tileset.GRASS)) {

                    buildWallForOnePoint(blue.nX, blue.nY);
                    for (int i = blue.nX - 2; i <= blue.nX + 2; i++) {
                        world[i][blue.nY] =  Tileset.FLOOR;
                    }
                    muda3();
                } else {
                    world[blue.nX][blue.nY] = Tileset.NOTHING;

                    muda3();
                }
            } else {
                muda3();
            }
        } else if (vI == 0  && vJ != 0) {
            if (blue.nY + vJ >= 0 && blue.nY + vJ < HEIGHT) {


                if (world[blue.nX + vI][blue.nY + vJ].equals(Tileset.WATER)) {

                    buildWallForOnePoint(blue.nX + vI, blue.nY + vJ);
                    buildWallForOnePoint(blue.nX, blue.nY);
                    for (int j = blue.nY - 3; j <= blue.nY + 3; j++) {
                        world[blue.nX][j] =  Tileset.FLOOR;
                    }
                    muda(blue.nX + vI, blue.nY + vJ);
                } else if (world[blue.nX + vI][blue.nY + vJ].equals(Tileset.GRASS)) {
                    buildWallForOnePoint(blue.nX, blue.nY);
                    for (int i = blue.nY - 2; i <= blue.nY + 2; i++) {
                        world[blue.nX][i] =  Tileset.FLOOR;
                    }
                    muda3();
                } else {
                    world[blue.nX][blue.nY] = Tileset.NOTHING;
                    muda3();
                }

            } else {
                muda3();

            }
        }
    }

    private void muda3() {
        Pointer randBlue;
        int randomIndex;
        if (blueLst.size() > 1) {
            randomIndex = rand.nextInt(blueLst.size() - 1);
            randBlue = blueLst.get(randomIndex);
            muda2(randBlue, randomIndex);
        } else if (blueLst.size() == 1) {
            randomIndex = 0;
            randBlue = blueLst.get(randomIndex);
            muda2(randBlue, randomIndex);
        }
    }



    public boolean ora(Integer targetX, Integer targetY, String buildToward) {


        Integer loopStartX = null;
        Integer loopEndX = null;

        Integer loopStartY = null;
        Integer loopEndY = null;

        Integer roomRandWidth = null;
        Integer roomRandHeight = null;


        roomRandWidth = RandomUtils.range(rand, 2, ROOMMAXWIDTH);
        roomRandHeight = RandomUtils.range(rand, 2, ROOMMAXHEIGHT);

        if (roomRandWidth % 2 != 0) {
            roomRandWidth -= 1;
        }

        if (roomRandHeight % 2 != 0) {
            roomRandHeight -= 1;
        }


        loopStartY = targetY;
        loopEndY = targetY + roomRandHeight;

        if (targetX + (3 * roomRandWidth) + 1 >= WIDTH
                || targetY + (3 * roomRandHeight) + 1 >= HEIGHT) {

            return true;
        }
        if (isOverLap(targetX, targetY, roomRandWidth * 3, roomRandHeight * 3)) {
            return true;

        }
        Integer tX = targetX, tY = targetY;
        Integer countX = roomRandWidth, countY = roomRandHeight;


        for (int x = targetX - 1; x < targetX + (3 * roomRandWidth) - 1; x++) {

            world[x][targetY + (3 * roomRandHeight) - 2] = Tileset.WALL;
            world[x][targetY - 1] = Tileset.WALL;
        }

        for (int y = targetY - 1; y < targetY + (3 * roomRandHeight) - 1; y++) {

            world[targetX + (3 * roomRandWidth) - 2][y] = Tileset.WALL;
            world[targetX - 1][y] = Tileset.WALL;
        }

        for (int x = targetX; x < targetX + (3 * roomRandWidth) - 2; x++) {
            for (int y = targetY; y < targetY + (3 * roomRandHeight) - 2; y++) {
                world[x][y] = Tileset.GRASS;
            }
        }



        //changeRoomFloor(targetX, targetY);
        roomsLocation.add(new Rooms(loopStartX, loopStartY));

        return false;

    }

    public void oraR(Integer numOfRooms) {

        boolean isoverlap = false;
        Integer temp1 = 0;
        int randIndex;

        if (availableForRoomLst.size() <= 0) {
            return;
        } else if (availableForRoomLst.size() == 1) {
            randIndex = 0;
        } else {
            randIndex = rand.nextInt(availableForRoomLst.size() - 1);
        }


        Rooms target = availableForRoomLst.get(randIndex);


        if (numOfRooms != 0) {



            Integer buildToward = rand.nextInt(1);

            switch (buildToward) {

                case 0:
                    isoverlap = ora(target.X, target.Y, "left");
                    break;

                case 1:
                    isoverlap = ora(target.X, target.Y, "right");
                    break;
                default:
                    break;

            }


            availableForRoomLst.remove(randIndex);

            if (isoverlap) {
                oraR(numOfRooms);
            } else {
                oraR(numOfRooms - 1);
            }

        }


    }

    public boolean isOverLap(Integer targetX, Integer targetY, Integer width, Integer height) {



        for (int i = targetX - 2; i <= targetX + width + 2; i++) {

            if ((world[i][targetY + height + 2].equals(Tileset.NOTHING)
                    || world[i][targetY + height + 2].equals(Tileset.WATER))

                    && (world[i][targetY + height / 2].equals(Tileset.NOTHING)
                    || world[i][targetY + height / 2].equals(Tileset.WATER))

                    && (world[i][targetY - 2].equals(Tileset.NOTHING)
                    || world[i][targetY - 2].equals(Tileset.WATER))

                    && (world[i][targetY].equals(Tileset.NOTHING)
                    || world[i][targetY].equals(Tileset.WATER))

                    && (world[i][targetY + height].equals(Tileset.NOTHING)
                    || world[i][targetY + height].equals(Tileset.WATER))) {
                continue;

            } else {
                return true;
            }

        }
        return false;
    }

    public void buildWallForOnePoint(Integer targetX, Integer targetY) {
        for (int x = -1; x < 2; x++) {
            if (targetX + x >= 0 && targetX + x < WIDTH) {

                for (int y = -1; y < 2; y++) {

                    if (targetY + y >= 0 && targetY + y < HEIGHT) {
                        if (x == 0 && y == 0) {

                            continue;


                        } else {
                            if (world[targetX + x][targetY + y].equals(Tileset.NOTHING)) {
                                world[targetX + x][targetY + y] = Tileset.WALL;
                            }

                        }
                    }


                }




            }

        }
    }

    public TETile[][] blackFog(Pointer avatar) {
        Integer targetX = avatar.nX, targetY = avatar.nY;
        blackFogWorld = new Helper().worldNOTHING(WIDTH, HEIGHT);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                blackFogWorld[x][y] = Tileset.NOTHING;
            }
        }

        for (int x = targetX - 2; x <= targetX + 2; x++) {
            for (int y = targetY - 2; y <= targetY + 2; y++) {

                blackFogWorld[x][y] = this.world[x][y];
            }
        }
        return blackFogWorld;

    }

    public TETile[][] copyCurrentWorld() {
        return TETile.copyOf(this.world);

    }

    public Pointer currentPosition() {
        return this.avatarPos;
    }

    public boolean isValidMove(String direction) {
        Integer targetX = currentPosition().nX, targetY = currentPosition().nY;
        switch (direction) {
            case "up":

                if (world[targetX][targetY + 1].description().equals("floor")
                        || world[targetX][targetY + 1].description().equals("grass")) {
                    return true;
                }
                break;
            case "down":

                if (world[targetX][targetY - 1].description().equals("floor")
                        || world[targetX][targetY - 1].description().equals("grass")) {
                    return true;
                }
                break;
            case "left":

                if (world[targetX - 1][targetY].description().equals("floor")
                        || world[targetX - 1][targetY].description().equals("grass")) {
                    return true;
                }
                break;
            case "right":

                if (world[targetX + 1][targetY].description().equals("floor")
                        || world[targetX + 1][targetY].description().equals("grass")) {
                    return true;
                }
                break;

            default:
                break;
        }
        return false;
    }

    public void changeAvatarlocation(String movement) {
        switch (movement) {
            case "up":
                world[avatarPos.nX][avatarPos.nY] = Tileset.FLOOR;
                avatarPos.nY += 1;
                world[avatarPos.nX][avatarPos.nY] = Tileset.AVATAR;
                break;
            case "down":
                world[avatarPos.nX][avatarPos.nY] = Tileset.FLOOR;
                avatarPos.nY -= 1;
                world[avatarPos.nX][avatarPos.nY] = Tileset.AVATAR;
                break;

            case "left":
                world[avatarPos.nX][avatarPos.nY] = Tileset.FLOOR;
                avatarPos.nX -= 1;
                world[avatarPos.nX][avatarPos.nY] = Tileset.AVATAR;
                break;
            case "right":
                world[avatarPos.nX][avatarPos.nY] = Tileset.FLOOR;
                avatarPos.nX += 1;
                world[avatarPos.nX][avatarPos.nY] = Tileset.AVATAR;
                break;


            default:
                break;
        }
    }

    public class Pointer implements Serializable {

        Integer nX;
        Integer nY;

        Integer[] facingVector;


        Pointer(Integer nX, Integer nY) {
            this.nX = nX;
            this.nY = nY;
        }
        Pointer(Integer nX, Integer nY, Integer[] facingVector) {
            this.nX = nX;
            this.nY = nY;
            this.facingVector = facingVector;
        }




    }

    public class Rooms implements Serializable {
        Integer X;
        Integer Y;

        int status;

        Rooms(Integer X, Integer Y) {
            this.X = X;
            this.Y = Y;
        }
        void setStatus(int x) {
            this.status = x;
        }




    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(69, 41);

        // initialize tiles
        TETile[][] world =  new Helper().worldNOTHING(69, 41);
        //Random qwe = new Random(2);
        Random qwe = new Random();
        Test3 t1 = new Test3(world, qwe, 69, 41);



        ter.renderFrame(world);

    }

}
