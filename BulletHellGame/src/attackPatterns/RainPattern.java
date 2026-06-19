package attackPatterns;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainPattern extends Pattern {

    private static class Bullet {
        float x, y;
        float speed;

        Bullet(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }

    private final List<Bullet> bullets = new ArrayList<>();
    private final Random random = new Random();

    private final int width;
    private final int height;

    private long lastSpawn = 0;
    private long durationStart;

    public RainPattern(int width, int height) {
        this.width = width;
        this.height = height;
        this.durationStart = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - lastSpawn > 80) {
            lastSpawn = System.currentTimeMillis();

            float x = random.nextInt(width);
            float y = -10;

            bullets.add(new Bullet(x, y, 2.5f + random.nextFloat() * 2));
        }

        for (Bullet b : bullets) {
            b.y += b.speed;
        }

        bullets.removeIf(b -> b.y > height);
        bullets.removeIf(b-> b.x > width);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0x095809));

        for (Bullet b : bullets) {
            g.fillOval((int)b.x, (int)b.y, 15, 20);
        }
    }

    @Override
    public List<Rectangle> getHitboxes() {
        List<Rectangle> list = new ArrayList<>();

        for (Bullet b : bullets) {
            list.add(new Rectangle((int)b.x, (int)b.y, 10, 15));
        }

        return list;
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - durationStart > 10000;
    }
}