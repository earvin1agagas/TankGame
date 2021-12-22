package TankGame.game.gameObjects.stationary;

import tankrotationexample.game.gameObjects.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakWall extends Wall {
    int x, y;
    private Rectangle hitBox;
    private BufferedImage wallImage;

    public UnBreakWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        this.hitBox = new Rectangle(x, y, wallImage.getWidth(), wallImage.getHeight());
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

    }

    @Override
    public boolean collision() {
        return false;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.wallImage, x, y, null);
    }
}
