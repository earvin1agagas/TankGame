package TankGame.game.gameObjects;

import java.awt.*;

public interface Collidable {

    public void collisionHandler(Collidable c);
    public Rectangle getHitBox();

}
