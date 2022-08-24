package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Engine engine = new Engine();
            engine.interactWithInputString(args[0]);
            System.out.println(engine.toString());
        } else {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
        }


        System.out.println(111111);
        TERenderer ter = new TERenderer();
        ter.initialize(69, 41);

        Engine engine = new Engine();
        System.out.println(111111);
        TETile[][] world = engine.interactWithInputString("n123333sw:q");

        //TETile[][] world = engine.interactWithInputString("l");
        ter.renderFrame(world);

        //Engine engine = new Engine();
        //engine.interactWithKeyboard();



    }
}
