package TankGame.game.gameObjects.moveable;


import TankGame.game.GameConstants;
import TankGame.game.Resource;
import TankGame.game.gameObjects.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObjects implements Collidable {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;



    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    //Rectangle used for hitboxes
    private Rectangle hitBox;
    private boolean ShootPressed;
    private ArrayList<Bullet> ammo;

    private int health = 100;
    private int lives = 5;



    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight()); //making hitbox same size as tank
        this.ammo = new ArrayList<>();

    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    //When keys are pressed
    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    //When keys are released
    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public int getVx() {
        return vx;
    }
    @Override
    public void setVx(int vx) {
        this.vx = vx;
    }
    @Override
    public int getVy() {
        return vy;
    }
    @Override
    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    @Override
    public void update() {
        //Independent ifs to detect multiple keys at a time

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && TRE.tickCount % 20 == 0) {
            //Testing if shooting button works!
            //System.out.println("I have shot a bullet");
            Bullet b = new Bullet(x, y, (int) angle, Resource.getResourceImage("bulletImage"));
            this.ammo.add(b);
        }
        /**
         * Enhanced for loop for each item in the ammo arraylist run the code in ().
         * Uses an iterator which while it is active no changes can be made to the list
         * Goes from beginning to end
         */
        for (int i = 0; i < ammo.size(); i++) {
            //Supposed to remove bullets that collide with other objects (tank, wall, etc.)
            if (ammo.get(i).isCollision()) {
                ammo.remove(i);
            } else {
                ammo.get(i).update();
                //this.ammo.forEach(bullet -> bullet.update());
            }
        }

        /**
         * for (int i = 0; i < this.ammo.size(); i++) {
         *  this.ammo.get(i).update();
         * }
         */
    }

    public int getXSplit(){
        if(x < GameConstants.GAME_SCREEN_WIDTH / 4) {
            x = GameConstants.GAME_SCREEN_WIDTH / 4;
        }

        if(x > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH /4) {
            x = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH /4;
        }

        return x - GameConstants.GAME_SCREEN_WIDTH / 4;
    }

    public int getYSplit(){

        if(y < GameConstants.GAME_SCREEN_HEIGHT / 2) {
            y = GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        if(y > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2) {
            y = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        return y - GameConstants.GAME_SCREEN_HEIGHT / 2;
    }


    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }


    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x,y);

    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }


    /**
     * I think the key to fixing my tanks in an invisible box is somewhere in here
     */
    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT -80) {
            y = GameConstants.GAME_SCREEN_HEIGHT -80;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int i) {
        this.health = health;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives() {
        this.lives = lives;
    }

    public void loseLife() {
        if(lives == 0) {
            health = 0;
        } else {
            lives -= 1;
            health = 100;
        }
    }

    @Override
    public void collisionHandler(Collidable c) {
        if (this.getHitBox().intersects(c.getHitBox())) {

            if (c instanceof Bullet) {

                if(health - 10 < 0) {
                    loseLife();
                    System.out.println("You lost a life!");
                } else {
                    health -= 10;
                    System.out.println("You lost 10 health!");
                }
            } else {

                 if (c instanceof Tank) {

                    if(isDownPressed() || isUpPressed()) {
                        System.out.println("Hit by tank");
                       x = (x - vx);
                       y = (y - vy);

                       //Damage health here!
                        if(health - 5 < 0) {
                            System.out.println("You lost a life!");
                            loseLife();

                        } else {
                            health -= 5;
                            System.out.println("You lost 5 health!");
                        }

                   }
                }

                        //Need to fix this should prevent tank from colliding with walls
                        Rectangle tankCollisionHandler = this.getHitBox().intersection(c.getHitBox());

                        if(tankCollisionHandler.height < tankCollisionHandler.width && this.y < tankCollisionHandler.y) { //moving up
                            y -= tankCollisionHandler.height / 2;
                        }

                        else if(tankCollisionHandler.height < tankCollisionHandler.width && this.y > c.getHitBox().y) { // moving down
                            y += tankCollisionHandler.height / 2;
                        }
                       else if(tankCollisionHandler.height > tankCollisionHandler.width && this.x < tankCollisionHandler.x) { //moving left
                           x -= this.getHitBox().width / 2;
                       }

                       else if(tankCollisionHandler.height > tankCollisionHandler.width && this.x > c.getHitBox().x) { // moving right
                           x += this.getHitBox().intersection(c.getHitBox()).width / 2;
                        }
                    }

                //This is supposed to be a collision handler for each bullet
                this.ammo.forEach(bullet -> {
                    c.collisionHandler(bullet);
                    c.collisionHandler(bullet);
                });
            }
    }
    //               if(t1.getHitBox().intersects(t2.getHitBox())) {
//                   if(t1.isDownPressed()) {
//                       t1.setX(t1.getX() - t1.getVx());
//                       t1.setY(t1.getY() - t1.getVy());
//                   }
//                   if(t2.isDownPressed()) {
//                       t2.setX(t2.getX() - t2.getVx());
//                       t2.setY(t2.getY() - t2.getVy());


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    //Not given in Java!
    //Can copy and paste for almost all drawing (for walls!)
    // For walls
    // Graphics2D g2d = (Graphics2D) g;
    //g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        //Draws all the bullets
        //  if(b != null) b.drawImage(g);
        //Draws all of the bullets
        /**
         * for (int i = 0; i < this.ammo.size(); i++) {
         *  this.ammo.get(i).drawImage(g);
         * }
         */
        this.ammo.forEach(bullet->bullet.drawImage(g));
        g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.getWidth() / 2.0, bounds.y + bounds.getHeight() / 2);
        g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());

    }





}
