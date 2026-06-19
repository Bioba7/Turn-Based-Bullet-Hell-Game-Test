package attackPatterns;

import java.awt.Graphics;
import java.util.List;
import java.awt.Rectangle;

public abstract class Pattern {

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract List<Rectangle> getHitboxes();

    public boolean isFinished() {
        return false;
    }
}