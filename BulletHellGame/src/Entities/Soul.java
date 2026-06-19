package Entities;

import java.awt.*;

public class Soul {
    private float x;
    private float y;

    private float vx;
    private float vy;
    private float speed = 4f;

    private boolean active = true;

    private int size = 11;

    private long invincibleUntil = 0;

    public Soul(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public void setInput(float dx, float dy) {
        this.vx = dx;
        this.vy = dy;
    }

    public void update() {

        if (!active) return;

        float length = (float) Math.sqrt(vx * vx + vy * vy);

        if (length != 0) {
            float nx = vx / length;
            float ny = vy / length;

            x += nx * speed;
            y += ny * speed;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }

    public boolean collides(Rectangle r) {
        return getBounds().intersects(r);
    }

    public boolean isInvincible() {
        return System.currentTimeMillis() < invincibleUntil;
    }

    public void setInvincible(long millis) {
        invincibleUntil = System.currentTimeMillis() + millis;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    public int getSize() { return size; }
}