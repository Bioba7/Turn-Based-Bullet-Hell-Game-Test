package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Scene extends Panel {

    protected ImageButton[] buttons;
    protected Runnable[] actions;
    protected int selectedIndex = 0;

    protected enum InputMode { HORIZONTAL, VERTICAL }
    protected InputMode inputMode = InputMode.VERTICAL;

    public Scene() {
        setLayout(null);
    }

    public abstract void onEnter();
    public abstract void onExit();

    public abstract void handleKey(KeyEvent e);
    public abstract void handleKeyReleased(KeyEvent e);

    protected void moveSelection(int dir) {
        if (buttons == null || buttons.length == 0) return;

        buttons[selectedIndex].setSelected(false);

        selectedIndex = (selectedIndex + dir + buttons.length) % buttons.length;

        buttons[selectedIndex].setSelected(true);
    }

    protected void activate() {
        if (actions != null && actions.length > 0) {
            actions[selectedIndex].run();
        }
    }

    public abstract void update();

    protected void resetSelection() {
        if (buttons == null || buttons.length == 0) return;

        for (ImageButton b : buttons) b.setSelected(false);

        selectedIndex = 0;
        buttons[0].setSelected(true);
    }
}