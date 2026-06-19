package GUI;

import java.awt.*;

public class DialogueBox extends Canvas {

    private static final int PADDING = 10;

    private final String dialogue;

    public DialogueBox(String dialogue) {

        this.dialogue = dialogue == null ? "" : dialogue;

        setFont(new Font("Dialog", Font.PLAIN, 16));

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(320,70);
    }

    @Override
    public void paint(Graphics g) {
        FontMetrics fm = g.getFontMetrics(getFont());

        int boxWidth = 320;
        int boxHeight = 70;

        int x = (getWidth() - boxWidth) / 2;
        int y = getHeight() - boxHeight - 15;

        g.setColor(Color.WHITE);

        g.drawRect(
                x,
                y,
                boxWidth,
                boxHeight
        );

        String[] lines = dialogue.split("\n");

        for (int i = 0; i < lines.length; i++) {

            g.drawString(
                    lines[i],
                    x + PADDING,
                    y + PADDING + fm.getAscent() + (i * fm.getHeight())
            );
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}