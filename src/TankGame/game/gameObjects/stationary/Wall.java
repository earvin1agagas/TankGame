package TankGame.game.gameObjects.stationary;

import tankrotationexample.game.gameObjects.Collidable;
import tankrotationexample.game.gameObjects.GameObjects;

import java.awt.*;

public abstract class Wall extends GameObjects implements Collidable {

    public Wall(){};

    public abstract void drawImage(Graphics g);

    public abstract void update();

    public abstract boolean collision();


}
