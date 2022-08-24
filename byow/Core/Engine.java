package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.proj3.Helper;
import byow.proj3.Test3;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Engine implements Serializable {
    TERenderer ter = new TERenderer();
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final int WIDTH = 69;
    public static final int HEIGHT = 41;
    private static final String[] WELCOMETEXTS = {"New Game (N)", "Load Game (L)", "Quit (Q)"};

    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT + 5);
        InputSource inputSource = new KeyboardInputSource();
        char c;
        String hud;
        Integer totalCount = 0;
        Test3 t2 = null;
        String randSeedString = new String();
        TETile[][] finalWorldFrame = new Helper().worldNOTHING(WIDTH, HEIGHT);
        LoadAndSave t9;
        renderWelcomeScreen(ter);
        StdDraw.enableDoubleBuffering();
        boolean operator1 = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                c = Character.toUpperCase(StdDraw.nextKeyTyped());
                totalCount += 1;
                if (c == 'L' && totalCount == 1) {
                    t9 = new LoadAndSave();
                    t2 = t9.load();
                    System.out.println("succ2222");
                    operator1 = true;
                } else if ((c == 'n' || c == 'N')) {

                    while (inputSource.possibleNextInput()) {
                        c = inputSource.getNextKey();

                        if (Character.isDigit(c)) {
                            randSeedString = randSeedString + c;
                        } else if (c == 's' || c == 'S') {
                            Long randomSeed = Long.parseLong(randSeedString);
                            Random rand = new Random(randomSeed);
                            t2 = new Test3(finalWorldFrame, rand, WIDTH, HEIGHT);
                            operator1 = true;
                            break;
                        }
                    }
                } else if (c == ':' && inputSource.possibleNextInput()) {
                    c = inputSource.getNextKey();
                    if (c == 'q' || c == 'Q') {
                        t9 = new LoadAndSave();
                        t9.save(t2);
                        System.exit(0);
                        break;
                    }
                } else if (c == 'W' && operator1) {
                    if (t2.isValidMove("up")) {
                        t2.changeAvatarlocation("up");
                    }
                } else if (operator1 && c == 'S') {
                    if (t2.isValidMove("down")) {
                        t2.changeAvatarlocation("down");
                    }
                } else if (operator1 && c == 'A') {
                    if (t2.isValidMove("left")) {
                        t2.changeAvatarlocation("left");

                    }
                } else if (operator1 && c == 'D') {
                    if (t2.isValidMove("right")) {
                        t2.changeAvatarlocation("right");
                    }
                }
                if (operator1) {
                    finalWorldFrame = t2.blackFog(t2.currentPosition());
                    ter.renderFrame(finalWorldFrame);
                }
            }
            if (operator1) {
                drawHUD(finalWorldFrame);

            }
        }

    }
    public TETile[][] interactWithInputString(String input) {
        char c;
        LoadAndSave t9;
        String randSeedString = new String();
        Long randomSeed;
        InputSource t1 = new StringInputDevice(input);
        Test3 t2 = null;
        TETile[][] finalWorldFrame = new Helper().worldNOTHING(WIDTH, HEIGHT);
        Integer totalCount = 0;
        while (t1.possibleNextInput()) {
            c = t1.getNextKey();
            totalCount += 1;
            if (c == 'l' && totalCount == 1) {
                t9 = new LoadAndSave();
                t2 = t9.load();
            }
            if (c == 'n' || c == 'N' && totalCount == 1) {
                while (t1.possibleNextInput()) {
                    c = t1.getNextKey();
                    if (Character.isDigit(c)) {
                        randSeedString = randSeedString + c;

                    } else if (c == 's' || c == 'S') {
                        randomSeed = Long.parseLong(randSeedString);

                        Random rand = new Random(randomSeed);

                        t2 = new Test3(finalWorldFrame, rand, WIDTH, HEIGHT);
                        break;
                    } else {
                        System.out.println("InputError: please restart the program");
                        return null;
                    }
                }
            }
            if (c == ':' && t1.possibleNextInput()) {
                c = t1.getNextKey();
                if (c == 'q' || c == 'Q') {
                    t9 = new LoadAndSave();
                    t9.save(t2);
                    System.exit(0);
                    System.out.println("done.");
                    break;
                }
            }
            if (c == 'W' || c == 'w') {
                if (t2.isValidMove("up")) {
                    t2.changeAvatarlocation("up");
                }
            }
            if (c == 'S' || c == 's') {
                if (t2.isValidMove("down")) {
                    t2.changeAvatarlocation("down");
                }
            }
            if (c == 'A' || c == 'a') {
                if (t2.isValidMove("left")) {
                    t2.changeAvatarlocation("left");
                }
            }
            if (c == 'D' || c == 'd') {
                if (t2.isValidMove("right")) {
                    t2.changeAvatarlocation("right");
                }
            }
        }
        finalWorldFrame = t2.copyCurrentWorld();
        return finalWorldFrame;
    }

    class LoadAndSave {
        public Test3 load() {

            Test3 t2 = null;
            File f = new File("./save_data.txt");


            if (f.exists()) {

                try {
                    FileInputStream fs = new FileInputStream(f);
                    ObjectInputStream os = new ObjectInputStream(fs);
                    t2 = (Test3) os.readObject();
                    t2.copyCurrentWorld();
                    System.out.println("succ!!!");

                } catch (FileNotFoundException e) {
                    System.out.println("file not found");
                    System.exit(0);
                } catch (IOException e) {
                    System.out.println(e);
                    System.exit(0);
                } catch (ClassNotFoundException e) {
                    System.out.println("class not found");
                    System.exit(0);
                }


            }

            return t2;
        }

        public void save(Test3 x) {

            File f = new File("./save_data.txt");
            try {
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fs = new FileOutputStream(f);
                ObjectOutputStream os = new ObjectOutputStream(fs);

                os.writeObject(x);
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            }
        }

    }


    private void renderWelcomeScreen(TERenderer ter2) {
        ter2.initialize(Engine.WIDTH, Engine.HEIGHT + 5);
        renderTextTitle("CS61B: >0<");
        renderTextCenter(WELCOMETEXTS);
        StdDraw.show();
    }
    private void renderTextTitle(String string) {
        Font font = new Font("Monaco", Font.BOLD, 36);
        StdDraw.setFont(font);

        StdDraw.setPenColor(Color.yellow);
        float x = (float) WIDTH / 2;
        float y = (float) 3 * HEIGHT / 4;
        StdDraw.text(x, y, string);
    }
    private void renderTextCenter(String[]strings) {
        assert strings.length == 3;

        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);

        StdDraw.setPenColor(Color.orange);

        float x = (float) WIDTH / 2;
        float y = (float) HEIGHT / 2;
        float yDiff = 3;
        for (String s : strings) {
            StdDraw.text(x, y, s);
            y -= yDiff;
        }

    }
    private void drawHUD(TETile[][]frame) {
        String hud;
        if (StdDraw.mouseX() < WIDTH && StdDraw.mouseY() < HEIGHT) {
            hud = frame[(int) StdDraw.mouseX()]
                    [(int) StdDraw.mouseY()].description();
        } else {
            hud = "nothing";
        }
        Font font = new Font("Monaco", Font.BOLD, 16);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.orange);
        StdDraw.text((float) WIDTH / 2, (float) HEIGHT + 3, hud);
        LocalDateTime now = LocalDateTime.now();
        StdDraw.text((float) 10, (float) HEIGHT + 3, DTF.format(now));
        StdDraw.show();
        ter.renderFrame(frame);
    }
}
