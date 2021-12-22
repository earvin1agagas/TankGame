package TankGame.game.gameObjects;

import java.awt.*;


public abstract class GameObjects implements Collidable{


    int x, y, vx, vy, angle, height, width;

    public abstract void drawImage(Graphics g);

    public abstract void update(); //For bullets and tanks

    //public abstract Rectangle getHitBox();



    public int getX() { return x; }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public abstract void collisionHandler(Collidable c);
    public boolean collision;

//    private String name;
//    public String getName() {
//        return name;
//    }
}
