package GUI;

import java.awt.*;

public class HealthBar extends Canvas {

    private int maxHP;
    private int currentHP;

    public HealthBar(int maxHP) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        setPreferredSize(new Dimension(200, 20));
    }

    public void setHP(int hp) {
        this.currentHP = Math.max(0, Math.min(hp, maxHP));
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        int width = 200;
        int height = 20;

        float ratio = (float) currentHP / maxHP;

        g.setColor(new Color(110, 39, 39));
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(30, 188, 115));
        g.fillRect(0, 0, (int)(width * ratio), height);

        g.setColor(Color.WHITE);
        g.drawRect(0, 0, width, height);
    }
    @Override
    public void update(Graphics g) {
        paint(g);
    }
}