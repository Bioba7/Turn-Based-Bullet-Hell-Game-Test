package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScene extends Scene {

    private ImageButton play = new ImageButton("Sprites/startButton.png","Sprites/startSelect.png");
    private ImageButton exit = new ImageButton("Sprites/exitButton.png","Sprites/exitSelect.png");

    public MenuScene(Runnable onPlay, Runnable onExit) {

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        Label title = new Label("TEST GAME", Label.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 120));

        Panel titlePanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc0 = new GridBagConstraints();
        gbc0.gridx = 0;
        gbc0.insets = new Insets(20, 0, 0, 0);
        gbc0.gridy = 0;
        titlePanel.add(title, gbc0);

        Panel buttonPanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();

        gbc1.gridx = 0;
        gbc1.insets = new Insets(0, 0, 30, 0);
        gbc1.fill = GridBagConstraints.NONE;
        gbc1.gridy = 0;
        buttonPanel.add(play, gbc1);
        gbc1.gridy = 1;
        buttonPanel.add(exit, gbc1);

        add(buttonPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        buttons = new ImageButton[]{play, exit};
        actions = new Runnable[]{
                onPlay,
                onExit
        };

        inputMode = InputMode.VERTICAL;
    }

    @Override
    public void onEnter() {
        resetSelection();
    }

    @Override
    public void onExit() {}

    @Override
    public void handleKey(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP -> moveSelection(-1);
            case KeyEvent.VK_DOWN -> moveSelection(1);

            case KeyEvent.VK_Z, KeyEvent.VK_ENTER -> activate();
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}

    @Override
    public void update() {}
}