package byow.Core;


public class Test33 {
/**
    private int WIDTH;
    private int HEIGHT;
    private static final int roomMaxWidth = 7;
    private static final int roomMaxHEIGHT = 7;

    private static final int maxRooms = 17;
    private static final int minRooms = 11;

    TETile[][] world;
    Random rand;

    ArrayList<Rooms> roomsLocation = new ArrayList<>();
    ArrayList<Pointer> blueLst = new ArrayList<>();
    ArrayList<Rooms> availableForRoomLst = new ArrayList<>();
    ArrayList<Rooms> hallwayStartPoint = new ArrayList<>();

    public Integer testCount = 0;


    public Test33(TETile[][] world, Random rand,Integer WIDTH, Integer HEIGHT) {
        this.world = world;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.rand = rand;


    }

    public void initialize() {

        TETile[][] tempWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tempWorld[x][y] = world[x][y];
            }
        }

        for (int x = 2; x < WIDTH - 1; x += 4) {
            for (int y = 2; y < HEIGHT - 1; y += 4) {

                if (world[x][y].equals(Tileset.NOTHING)) {


                    if (x + 2 != WIDTH - 1 && y != 2) {
                        //world[x + 2][y] = Tileset.MOUNTAIN;
                        availableForRoomLst.add(new Rooms(x, y));
                    }

                    if (y == 2) {
                        hallwayStartPoint.add(new Rooms(x, y));
                    }

                    world[x][y] = Tileset.WATER;


                }



            }

        }

        Integer numOfRooms = RandomUtils.range(rand,minRooms , maxRooms);
        oraR(numOfRooms);
        Integer randIndex = rand.nextInt(hallwayStartPoint.size() - 1);
        Integer tX = hallwayStartPoint.get(randIndex).X, tY = hallwayStartPoint.get(randIndex).Y;


        muda(tX, tY);


        for (Rooms i : roomsLocation) {

        }

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {

                if (world[x][y].equals(Tileset.WATER)) {

                }

            }
        }
    }

    public void muda(Integer targetX, Integer targetY) {
        Pointer randBlue;
        int randomIndex;

        muda1(targetX,targetY);


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



        for (int x = -2; x < 3; x += 4) {
            if (targetX + x > 0 && targetX + x < WIDTH - 1) {
                if (world[targetX + x][targetY].equals(Tileset.NOTHING)) {


                    world[targetX + x][targetY] = Tileset.SAND;
                    blueLst.add(new Pointer(targetX + x, targetY, new Integer[] {x, 0}));
                }
            }

        }




        for (int y = -2; y < 3; y += 4) {
            if (targetY + y > 0 && targetY + y < HEIGHT - 1) {
                if (world[targetX][targetY + y].equals(Tileset.NOTHING)) {




                    world[targetX][targetY + y] = Tileset.SAND;



                    blueLst.add(new Pointer(targetX, targetY + y, new Integer[] {0, y}));

                }
            }

        }






    }

    public void muda2(Pointer Blue, int IndexOfBlueLst) {
        Pointer randBlue;
        int randomIndex;

        Integer vI = Blue.facingVector[0];
        Integer vJ = Blue.facingVector[1];


        Pointer temp999 = blueLst.remove(IndexOfBlueLst);




        if (vI != 0 && vJ == 0) {

            if (Blue.nX + vI >= 0 && Blue.nX + vI < WIDTH) {




                if (world[Blue.nX + vI][Blue.nY + vJ].equals(Tileset.WATER)) {
                    buildWallForOnePoint(Blue.nX + vI, Blue.nY + vJ);
                    buildWallForOnePoint(Blue.nX, Blue.nY);
                    for (int i = Blue.nX - 2; i <= Blue.nX + 2; i++) {
                        world[i][Blue.nY] =  Tileset.FLOOR;

                    }



                    muda(Blue.nX + vI, Blue.nY + vJ);

                } else if (world[Blue.nX + vI][Blue.nY + vJ].equals(Tileset.GRASS)) {

                    buildWallForOnePoint(Blue.nX, Blue.nY);
                    for (int i = Blue.nX - 1; i <= Blue.nX + 1; i++) {
                        world[i][Blue.nY] =  Tileset.FLOOR;

                    }

                    //changeRoomFloor(Blue.nX + vI, Blue.nY + vJ);

                    if (blueLst.size() > 1) {
                        randomIndex = rand.nextInt(blueLst.size() - 1);
                        randBlue = blueLst.get(randomIndex);
                        muda2(randBlue, randomIndex);
                    } else if (blueLst.size() == 1) {
                        randomIndex = 0;
                        randBlue = blueLst.get(randomIndex);
                        muda2(randBlue, randomIndex);
                    }



                } else {
                    world[Blue.nX][Blue.nY] = Tileset.NOTHING;

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

            } else {
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

        } else if (vI == 0  && vJ != 0) {
            if (Blue.nY + vJ >= 0 && Blue.nY + vJ < HEIGHT) {


                if (world[Blue.nX + vI][Blue.nY + vJ].equals(Tileset.WATER)) {

                    buildWallForOnePoint(Blue.nX + vI, Blue.nY + vJ);
                    buildWallForOnePoint(Blue.nX, Blue.nY);
                    for (int j = Blue.nY - 2; j <= Blue.nY + 2; j++) {
                        world[Blue.nX][j] =  Tileset.FLOOR;

                    }


                    muda(Blue.nX + vI, Blue.nY + vJ);



                } else if (world[Blue.nX + vI][Blue.nY + vJ].equals(Tileset.GRASS)) {




                    buildWallForOnePoint(Blue.nX, Blue.nY);
                    for (int i = Blue.nY - 1; i <= Blue.nY + 1; i++) {
                        world[Blue.nX][i] =  Tileset.FLOOR;

                    }


                    if (blueLst.size() > 1) {
                        randomIndex = rand.nextInt(blueLst.size() - 1);
                        randBlue = blueLst.get(randomIndex);
                        muda2(randBlue, randomIndex);
                    } else if (blueLst.size() == 1) {
                        randomIndex = 0;
                        randBlue = blueLst.get(randomIndex);
                        muda2(randBlue, randomIndex);
                    }

                } else {
                    world[Blue.nX][Blue.nY] = Tileset.NOTHING;


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



            } else {
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


        }



    }





    public boolean ora(Integer targetX, Integer targetY, String buildToward) {



        Integer loopStartX = null;
        Integer loopEndX = null;

        Integer loopStartY = null;
        Integer loopEndY = null;

        Integer roomRandWidth = null;
        Integer roomRandHeight = null;


        roomRandWidth = RandomUtils.range(rand,2 ,roomMaxWidth);
        roomRandHeight = RandomUtils.range(rand,2 ,roomMaxHEIGHT);

        while (targetY + roomRandHeight >= HEIGHT - 1) {
            roomRandHeight -= 1;

        }



        loopStartY = targetY;
        loopEndY = targetY + roomRandHeight;


        switch (buildToward) {
            case "right":



                loopStartX = targetX;
                loopEndX = targetX + roomRandWidth;

                if (!(loopEndX + 1 < WIDTH)) {

                    return ora(targetX, targetY, "left");


                } else if (isOverLap(loopStartX, loopStartY, roomRandWidth, roomRandHeight)) {
                    return true;

                } else {
                    break;
                }





            case "left":

                loopEndX = targetX;
                loopStartX = targetX - roomRandWidth;
                if (!(loopStartX - 1 >= 0)) {



                    return ora(targetX, targetY, "right");

                } else if (isOverLap(loopStartX, loopStartY, roomRandWidth, roomRandHeight)) {
                    return true;

                } else {
                    break;
                }


        }


        for (int x = loopStartX - 1; x <= loopEndX + 1; x++) {


            world[x][loopStartY - 1] = Tileset.WALL;
            world[x][loopEndY + 1] = Tileset.WALL;


        }

        for (int y = loopStartY - 1; y <= loopEndY + 1; y++) {


            world[loopStartX - 1][y] = Tileset.WALL;
            world[loopEndX + 1][y] = Tileset.WALL;

        }



        for (int x = loopStartX; x <= loopEndX; x++) {
            for (int y = loopStartY; y <= loopEndY; y++) {
                world[x][y] = Tileset.GRASS;
            }
        }

        roomsLocation.add(new Rooms(loopStartX, loopStartY));

        return false;

    }

    public void oraR(Integer numOfRooms) {

        boolean isoverlap = false;
        Integer temp1 = 0;


        int randIndex = rand.nextInt(availableForRoomLst.size() - 1);
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
        for (int i = targetX - 1; i <= targetX + width + 1; i++) {

            if ((world[i][targetY + height].equals(Tileset.NOTHING)
                    || world[i][targetY + height].equals(Tileset.WATER))

                    && (world[i][targetY + height / 2].equals(Tileset.NOTHING)
                    || world[i][targetY + height / 2].equals(Tileset.WATER))

                    && (world[i][targetY].equals(Tileset.NOTHING)
                    || world[i][targetY].equals(Tileset.WATER))) {

            } else {
                return true;
            }

        }
        return false;
    }


    public void changeRoomFloor(Integer targetX, Integer targetY) {
        world[targetX][targetY] = Tileset.FLOOR;
        for (int x = -1; x < 2; x++) {
            if (targetX + x >= 0 && targetX + x < WIDTH) {

                for (int y = -1; y < 2; y++) {

                    if (targetY + y >= 0 && targetY + y < HEIGHT) {
                        if (world[targetX + x][targetY + y].equals(Tileset.GRASS)) {
                            changeRoomFloor(targetX + x, targetY + y);
                        }
                    }


                }




            }

        }



    }

    public void wipeOutClosedRooms(Integer targetX, Integer targetY) {
        world[targetX][targetY] = Tileset.TREE;
        for (int x = -1; x < 2; x++) {
            if (targetX + x >= 0 && targetX + x < WIDTH) {

                for (int y = -1; y < 2; y++) {

                    if (targetY + y >= 0 && targetY + y < HEIGHT) {
                        if (world[targetX + x][targetY + y].equals(Tileset.GRASS)) {
                            wipeOutClosedRooms(targetX + x, targetY + y);
                        } else if (world[targetX + x][targetY + y].equals(Tileset.FLOOR)) {

                        }
                    }


                }




            }

        }
        return;
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


    public class Pointer {

        Integer nX;
        Integer nY;
        Pointer prePoint;
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

    public class Rooms {
        Integer X;
        Integer Y;

        Integer width;
        Integer height;
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
        Random qwe = new Random(16);
        Test33 t1 = new Test33(world, qwe, 69, 41);



        /*
        t1.ora(6,2);
        t1.ora(38,14);

        t1.ora(6,30);
        t1.ora(22,14);
        t1.ora(26,30);
        */



}



