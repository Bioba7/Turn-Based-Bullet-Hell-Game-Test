package attackPatterns;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SweepWallPattern extends Pattern {

    private static class Wall {
        int x;
        int gapY;

        Wall(int x, int gapY) {
            this.x = x;
            this.gapY = gapY;
        }
    }

    private final List<Wall> walls = new ArrayList<>();

    private final int arenaWidth;
    private final int arenaHeight;

    private final int wallWidth = 40;
    private final int gapHeight = 80;
    private final int speed = 4;

    public SweepWallPattern(int arenaWidth, int arenaHeight) {

        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;

        int wallCount = 5;

        for (int i = 0; i < wallCount; i++) {

            int startX = -(i + 1) * 150;

            int gapY = (int)(Math.random() * (arenaHeight - gapHeight));

            walls.add(new Wall(startX, gapY));
        }
    }

    @Override
    public void update() {

        for (Wall wall : walls) {
            wall.x += speed;
        }
    }

    @Override
    public void render(Graphics g) {

        g.setColor(new Color(45, 101, 49, 255));

        for (Wall wall : walls) {

            g.fillRect(
                    wall.x,
                    0,
                    wallWidth,
                    wall.gapY
            );

            g.fillRect(
                    wall.x,
                    wall.gapY + gapHeight,
                    wallWidth,
                    arenaHeight - (wall.gapY + gapHeight)
            );
        }
    }

    @Override
    public List<Rectangle> getHitboxes() {

        List<Rectangle> hitboxes = new ArrayList<>();

        for (Wall wall : walls) {

            hitboxes.add(
                    new Rectangle(
                            wall.x,
                            0,
                            wallWidth,
                            wall.gapY
                    )
            );

            hitboxes.add(
                    new Rectangle(
                            wall.x,
                            wall.gapY + gapHeight,
                            wallWidth,
                            arenaHeight - (wall.gapY + gapHeight)
                    )
            );
        }

        return hitboxes;
    }

    @Override
    public boolean isFinished() {

        for (Wall wall : walls) {
            if (wall.x <= arenaWidth) {
                return false;
            }
        }

        return true;
    }
}