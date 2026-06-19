package GUI;

import java.awt.*;
import Entities.Character;

public class AttackBar extends Canvas {

    private float progress = 0f;
    private boolean active = false;

    public AttackBar() {
        setPreferredSize(new Dimension(50, 200));
    }

    public void start() {
        progress = 0f;
        active = true;
        setVisibleBar(true);
    }

    public void update() {
        if (!active) return;

        progress += 0.02f;

        if (progress >= 1f) {
            progress = 1f;
        }

        repaint();
    }

    public boolean isFinished() {
        return progress >= 1f;
    }

    public void setVisibleBar(boolean v) {
        active = v;
        this.setVisible(v);
    }

    @Override
    public void paint(Graphics g) {
        int x = 10;
        int y = 10;
        int width = 20;
        int height = 200;

        g.setColor(new Color(67,69,104));
        g.drawRect(x, y, width, height);

        g.setColor(new Color(57,59,94));
        g.fillRect(x, y + height - (int)(height * progress), width, (int)(height * progress));
    }
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public int calculateDamage(Character attacker, Character defender) {
        double multiplier = 0.85 + Math.random() * 0.30;

        int damage = (int)(attacker.getAttack() * multiplier - defender.getDefense() / 2.0);

        return Math.max(1, damage);
    }
}