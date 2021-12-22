package TankGame.game;

import tankrotationexample.game.gameObjects.TRE;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.imageio.ImageIO.read;

public class Resource {

    private static Map<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();
        try {
            Resource.resources.put("t1img", read(TRE.class.getClassLoader().getResource("Tank1.gif")));
            Resource.resources.put("t2img", read(TRE.class.getClassLoader().getResource("Tank2.gif")));
            Resource.resources.put("bulletImage", read(TRE.class.getClassLoader().getResource("Shell.gif")));
            Resource.resources.put("breakWall1", read(TRE.class.getClassLoader().getResource("Wall1.gif")));
            Resource.resources.put("breakWall2", read(TRE.class.getClassLoader().getResource("Wall2.gif")));
            Resource.resources.put("unbreakableWall", read(TRE.class.getClassLoader().getResource("unbreakWall.gif")));
            Resource.resources.put("hearts", read(TRE.class.getClassLoader().getResource("heart.png")));
            Resource.resources.put("t1Win", read(TRE.class.getClassLoader().getResource("player-1-wins.jpg")));
            Resource.resources.put("t2Win", read(TRE.class.getClassLoader().getResource("player-2-wins.jpg")));

        } catch (IOException e) {
            e.printStackTrace();
            //Don't wanna continue game if something is wrong!
            System.exit(-5);
        }

    }

    public static BufferedImage getResourceImage(String key) {
        return Resource.resources.get(key);
    }

}
