/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame.game.gameObjects;


import tankrotationexample.game.GameConstants;
import tankrotationexample.game.Launcher;
import tankrotationexample.game.Resource;
import tankrotationexample.game.gameObjects.moveable.Tank;
import tankrotationexample.game.gameObjects.moveable.TankControl;
import tankrotationexample.game.gameObjects.stationary.BreakWall;
import tankrotationexample.game.gameObjects.stationary.UnBreakWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Launcher lf;
    private long tick = 0;
    ArrayList<GameObjects> gameObjects;
    public static long tickCount = 0;
    private static boolean playerWins = false;




    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (!playerWins) {
                this.tick++;
                this.gameObjects.forEach(gameObject -> gameObject.update()); // Update entire game
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                tickCount++;
               /**
                * Need to fix this collision
                * with an abstraction can do:
                * if(obj instanceof Tank) {
                *   obj has hit a tank
                * }
                */
               Tank t2 = (Tank) this.gameObjects.get(this.gameObjects.size() - 1);
               Tank t1 = (Tank) this.gameObjects.get(this.gameObjects.size() - 2);
               t1.collisionHandler(t2);
               t2.collisionHandler(t1);




//               if(t1.getHitBox().intersects(t2.getHitBox())) {
//                   if(t1.isDownPressed()) {
//                       t1.setX(t1.getX() - t1.getVx());
//                       t1.setY(t1.getY() - t1.getVy());
//                   }
//                   if(t2.isDownPressed()) {
//                       t2.setX(t2.getX() - t2.getVx());
//                       t2.setY(t2.getY() - t2.getVy());
//                   }
//                   System.out.println("Tanks have collided");
//               }                if(this.tick > 500){
//                    this.lf.setFrame("end");
//                    return;
//                }

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */

               /**
                *  When there is a game ending condition add "return" to end game
                */



            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){

        this.tick = 0;
        Tank t2 = (Tank) this.gameObjects.get(this.gameObjects.size() - 1);
        Tank t1 = (Tank) this.gameObjects.get(this.gameObjects.size() - 2);

        t1.setX(200);
        t1.setY(200);

        t2.setX(800);
        t2.setY(800);


    }



    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);


        this.gameObjects = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */


            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("No data in file!");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");

                for(int curCol= 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {
                        case "2": //Breakable wall
                            BreakWall br = new BreakWall(curCol * 30, curRow * 30,  Resource.getResourceImage("breakWall1"));
                            this.gameObjects.add(br);
                            break;
                        case "3": //Unbreakable wall
                        case "9":
                            UnBreakWall ubr = new UnBreakWall(curCol * 30, curRow * 30, Resource.getResourceImage("unbreakableWall"));
                            this.gameObjects.add(ubr);
                            break;
                    }

                }

            }


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        Tank t1 = new Tank(200, 200, 0, 0, 0, Resource.getResourceImage("t1img"));
        //need new tank img for player 2
        Tank t2 = new Tank(800, 800, 0, 0, 180, Resource.getResourceImage("t2img"));

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.gameObjects.add(t1);
        this.gameObjects.add(t2);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc2);

//        for(int i = 0; i < gameObjects.size(); i++) {
//            System.out.println(gameObjects.get(i).getName());
//        }

    }






    @Override
    //This function draws the entire world
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);

        //Should draw entire game
        this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));

        Tank t2 = (Tank) this.gameObjects.get(this.gameObjects.size() - 1);
        Tank t1 = (Tank) this.gameObjects.get(this.gameObjects.size() - 2);
        //world.getSubimage() to grab sub sections of image for minimap or split screen

       //Draw Splitscreen need to fix this!!!
        BufferedImage leftHalf = world.getSubimage(t1.getXSplit(), t1.getYSplit(), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(t2.getXSplit(), t2.getYSplit(), GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 5,0,null);


        //Drawing lives for tank 1
        int heartPlace;
        for(int i = 1; i <= t1.getLives(); i++) {

            heartPlace = ((Resource.getResourceImage("hearts").getWidth() + 20) * i) / 2 + 120;
            g2.drawImage(Resource.getResourceImage("hearts"), heartPlace, 0, null);
        }
        //Drawing lives for tank 2
        for(int i = 1; i <= t2.getLives(); i++) {

            heartPlace = ((Resource.getResourceImage("hearts").getWidth() + 20) * i)/2 + GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2;

            g2.drawImage(Resource.getResourceImage("hearts"), heartPlace, 0, null);
        }
        //Drawing health for tank 1 here
        g2.setColor(Color.RED);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH /4, 30, 2*t1.getHealth(), 20);

        //Drawing health for tank 2 here
        g2.setColor(Color.BLUE);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH /4, 30, 2*t2.getHealth(), 20);

        //Drawing win conditions here!
        if(t1.getLives() == 0) {
            g2.drawImage(Resource.getResourceImage("t2Win"), 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, null);
            playerWins = true;
        }

        if(t2.getLives() == 0) {
            g2.drawImage(Resource.getResourceImage("t1Win"), 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, null);
            playerWins = true;
        }

        //Draw minimap stuff here, play around with this!
        BufferedImage mm = world.getSubimage(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        g2.scale(.10, .10);
        g2.drawImage(mm, 0, 0, null);


        /**
         * To get center of split screen for left side:
         *  Watch term project drawing hints timestamp 19:00
         */
        /**
         * for split screen checkBorder function
         * gives center of each splitscreen
         * x - screen width / 4
         * y - screen height / 2
         *
         *
         */


    }

}
