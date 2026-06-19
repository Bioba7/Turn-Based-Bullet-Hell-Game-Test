package GUI;

import Entities.Character;
import attackPatterns.AreaDangerPattern;
import Entities.Soul;
import attackPatterns.*;

import java.awt.*;

public class BattleBox extends Panel {
    private Image buffer;
    private Graphics bufferGraphics;

    private final int width = 400;
    private final int height = 250;

    final Soul soul = new Soul(150, 120);

    Pattern pattern;

    private float inputX;
    private float inputY;

    private Image soulSprite;

    public BattleBox() {
        setBackground(Color.black);
        soulSprite = Toolkit.getDefaultToolkit().getImage(("Sprites/soul.png"));
        setPreferredSize(new Dimension(400, 250));
    }

    public void centerSoul() {

        soul.setX(
                (getWidth() - soul.getSize()) / 2f
        );

        soul.setY(
                (getHeight() - soul.getSize()) / 2f
        );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400,250);
    }

    public void startWave(int w, int h) {
        Pattern[] patterns = {new RainPattern(w, h), new AreaDangerPattern(w,h), new SweepWallPattern(w, h)};
        pattern = patterns[((int)(Math.random() * 3))];
    }

    public void setInput(float x, float y) {
        inputX = x;
        inputY = y;
        soul.setInput(x, y);
    }

    public void update() {
        if(pattern != null) {
            pattern.update();
            if(pattern.isFinished()) {
                pattern = null;
                waveFinished();
            }
        }

        soul.update();
        int margin = soul.getSize();

        soul.setX(Math.max(0, Math.min(soul.getX(), getWidth() - margin)));

        soul.setY(Math.max(0, Math.min(soul.getY(), getHeight() - margin)));

        repaint();
    }

    public int calculateDamage(Character attacker, Character defender) {
        double multiplier = 0.85 + Math.random() * 0.30;

        int damage = (int)(attacker.getAttack() * multiplier - defender.getDefense() / 2.0);

        return Math.max(1, damage);
    }

    @Override
    public void paint(Graphics g) {
        if (buffer == null || buffer.getWidth(this) != getWidth() || buffer.getHeight(this) != getHeight()) {

            buffer = createImage(getWidth(), getHeight());
            bufferGraphics = buffer.getGraphics();
        }

        bufferGraphics.setColor(getBackground());
        bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

        bufferGraphics.setColor(Color.WHITE);
        bufferGraphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        if (pattern != null) {
            pattern.render((Graphics2D) bufferGraphics);
        }

        if (soulSprite != null) {

            bufferGraphics.drawImage(
                    soulSprite,
                    (int) soul.getX(),
                    (int) soul.getY(),
                    soul.getSize(),
                    soul.getSize(),
                    this
            );

        } else {

            bufferGraphics.setColor(Color.RED);
            bufferGraphics.fillRect(
                    (int) soul.getX(),
                    (int) soul.getY(),
                    soul.getSize(),
                    soul.getSize()
            );
        }

        g.drawImage(buffer, 0, 0, this);
    }

    public boolean waveFinished() {
        return pattern == null;
    }
}