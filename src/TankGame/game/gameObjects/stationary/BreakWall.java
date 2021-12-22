package TankGame.game.gameObjects.stationary;

import tankrotationexample.game.gameObjects.Collidable;
import tankrotationexample.game.gameObjects.moveable.Bullet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall {
    int x, y;
    private Rectangle hitBox;
    private BufferedImage breakWall1;
    private boolean collision = false;


    public BreakWall(int x, int y, BufferedImage breakWall1) {
        this.x = x;
        this.y = y;
        this.breakWall1 = breakWall1;
        this.hitBox = new Rectangle(x, y, breakWall1.getWidth(), breakWall1.getHeight());
    }
    @Override
    public void update() {

    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    @Override
    public void collisionHandler(Collidable c) {
        if(c instanceof Bullet) {
            if(this.getHitBox().intersects(c.getHitBox())) {
            collision = true;
            }
        }
    }

    @Override
    public boolean collision() {
        return collision;
    }


    @Override
    public void drawImage(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.breakWall1, x, y, null);

        }
    }

