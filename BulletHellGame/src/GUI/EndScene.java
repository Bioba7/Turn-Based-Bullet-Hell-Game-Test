package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EndScene extends Scene{

    private ImageButton retry = new ImageButton("Sprites/retryButton.png","Sprites/retrySelect.png");
    private ImageButton exit = new ImageButton("Sprites/exitButton.png","Sprites/exitSelect.png");

    private Label title;

    public EndScene(Runnable onRetry, Runnable onExit) {

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        title = new Label("",Label.CENTER);
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

        gbc1.gridy = 0;
        gbc1.weightx = 1.0;
        gbc1.insets = new Insets(0, 0, 30, 0);
        gbc1.fill = GridBagConstraints.NONE;
        gbc1.gridx = 0;
        buttonPanel.add(retry, gbc1);
        gbc1.gridx = 1;
        buttonPanel.add(exit, gbc1);

        add(buttonPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        buttons = new ImageButton[]{retry, exit};
        actions = new Runnable[]{
                onRetry,
                onExit
        };

        inputMode = InputMode.VERTICAL;
    }

    public void setHeader(String header) {
        title.setText(header);
        validate();
        repaint();
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

            case KeyEvent.VK_RIGHT -> moveSelection(-1);
            case KeyEvent.VK_LEFT -> moveSelection(1);

            case KeyEvent.VK_Z, KeyEvent.VK_ENTER -> activate();
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}

    @Override
    public void update() {}
}
