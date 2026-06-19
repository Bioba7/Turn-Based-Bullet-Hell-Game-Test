package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Window extends Frame {

    private CardLayout cards = new CardLayout();
    private Panel root = new Panel(cards);
    private Timer gameLoop;

    private MenuScene menu;
    private BattleScene battle;
    private EndScene end;

    private Scene currentScene;

    public Window() {
        super("Game");
        setSize(1600, 900);

        menu = new MenuScene(
                this::showBattle,
                this::closeWindow
        );

        battle = new BattleScene(
                this::showWin,
                this::showLose
        );

        end = new EndScene(
                this::retryBattle,
                this::closeWindow
        );


        root.add(menu, "MENU");
        root.add(battle, "BATTLE");
        root.add(end, "END");

        add(root);

        setupInput();

        gameLoop = new Timer(16, e -> {
            if (currentScene != null) {
                currentScene.update();
            }

        });
        gameLoop.start();

        showMenu();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    private void setupInput() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               if (currentScene != null)
                    currentScene.handleKey(e);

            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (currentScene != null)
                    currentScene.handleKeyReleased(e);
            }
        });
    }

    private void setScene(String name, Scene scene) {

        if (currentScene != null)
            currentScene.onExit();

        currentScene = scene;
        cards.show(root, name);

        currentScene.onEnter();

        requestFocus();
    }

    private void showMenu() {
        setScene("MENU", menu);
    }

    private void showWin() {
        end.setHeader("YOU WON!");
        setScene("END", end);
    }

    private void showLose() {
        end.setHeader("YOU LOST!");
        setScene("END", end);
    }

    private void showBattle() {
        setScene("BATTLE", battle);
    }

    private void retryBattle(){
        root.remove(battle);
        battle = new BattleScene(this::showWin, this::showLose);
        root.add(battle, "BATTLE");
        root.validate();
        root.doLayout();
        setScene("BATTLE", battle);
        root.repaint();
    }

    public void closeWindow() {
        dispose();
        gameLoop.stop();
    }

    public static void main(String[] args) {
        new Window();
    }
}