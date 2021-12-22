package TankGame.game.gameObjects.stationary;

import TankGame.game.gameObjects.Collidable;
import TankGame.game.gameObjects.GameObjects;

import java.awt.*;

public abstract class Wall extends GameObjects implements Collidable {

    public Wall(){};

    public abstract void drawImage(Graphics g);

    public abstract void update();

    public abstract boolean collision();


}
