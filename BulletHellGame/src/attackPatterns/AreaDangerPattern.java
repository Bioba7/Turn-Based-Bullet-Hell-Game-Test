package attackPatterns;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDangerPattern extends Pattern {

    private final List<Rectangle> dangerZones = new ArrayList<>();

    private boolean active = false;

    private final long startTime;

    private final int warningTime = 1000;
    private final int activeTime = 2000;

    public AreaDangerPattern(int boxWidth, int boxHeight) {

        startTime = System.currentTimeMillis();

        int zoneCount = 2 + (int)(Math.random() * 3);

        for (int i = 0; i < zoneCount; i++) {

            int width = 40 + (int)(Math.random() * 80);
            int height = 40 + (int)(Math.random() * 80);

            int x = (int)(Math.random() * Math.max(1, boxWidth - width));
            int y = (int)(Math.random() * Math.max(1, boxHeight - height));

            dangerZones.add(new Rectangle(x, y, width, height)
            );
        }
    }

    @Override
    public void update() {

        long elapsed = System.currentTimeMillis() - startTime;

        active = elapsed >= warningTime && elapsed < warningTime + activeTime;
    }

    @Override
    public void render(Graphics g) {

        if (!active) {

            g.setColor(Color.ORANGE);

            for (Rectangle zone : dangerZones) {

                int centerX = zone.x + zone.width / 2;
                int centerY = zone.y + zone.height / 2;

                g.fillRect(
                        centerX - 5,
                        centerY - 5,
                        10,
                        10
                );
            }

            return;
        }

        g.setColor(new Color(255, 0, 0, 100));

        for (Rectangle zone : dangerZones) {

            g.fillRect(
                    zone.x,
                    zone.y,
                    zone.width,
                    zone.height
            );
        }
    }

    @Override
    public List<Rectangle> getHitboxes() {

        if (!active) {
            return new ArrayList<>();
        }

        return new ArrayList<>(dangerZones);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime > warningTime + activeTime;
    }
}