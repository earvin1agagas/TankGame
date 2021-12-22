package TankGame.game.gameObjects.moveable;

import tankrotationexample.game.GameConstants;
import tankrotationexample.game.gameObjects.Collidable;
import tankrotationexample.game.gameObjects.GameObjects;
import tankrotationexample.game.gameObjects.stationary.BreakWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObjects implements Collidable {
    int x, y, vx, vy, angle;
    int R = 7;
    private BufferedImage bulletImage;
    private Rectangle hitBox;
    private boolean collision = false;


    public Bullet(int x, int y, int angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        this.hitBox = new Rectangle(x, y, this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }

    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void checkBorder(){
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    public boolean isCollision() {
        return collision;
    }
    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }


    @Override
    public void update() {

        moveForwards();
    }



    @Override
    public void collisionHandler(Collidable c){
        if(this.getHitBox().intersects(c.getHitBox())) {
            if(c instanceof Tank) {
                System.out.println("Bullet hit tank!");
                collision = true;
            }

            if(c instanceof BreakWall) {
                System.out.println("Bullet hit breakable wall!");
                collision = true;
            }
        }
    }


    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bulletImage.getWidth() / 2.0, this.bulletImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.bulletImage, rotation, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(x, y, this.bulletImage.getWidth(), this.bulletImage.getHeight());

    }
}
