package Entities;

import java.awt.*;

public abstract class Entity extends Canvas {
    protected String name;
    public Image sprite;
    private static int seed = 0;
    final int id = ++seed;

    public Entity(String name, String sprite) {
        this.name = name;
        this.sprite = Toolkit.getDefaultToolkit().getImage(sprite);
    }

    @Override
    public void paint(Graphics g) {
        Image img = sprite;
        g.drawImage(img, 0,0,getWidth(),getHeight(),this);
    }
}
