package GUI;

import Entities.Character;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class BattleScene extends Scene {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private final BattleBox battleBox = new BattleBox();
    private final AttackBar attackBar = new AttackBar();
    private final DialogueBox buffDialogue = new DialogueBox("Your attack has been buffed!");
    private final DialogueBox healDialogue = new DialogueBox("You healed yourself");

    private Panel boxSection;
    private CardLayout boxCards;

    private Character enemy = new Character("Bisporeus","Sprites/enemy.png",120,15,8);
    private Character player = new Character("Player","Sprites/player.png",100,20,10);

    private HealthBar playerHP = new HealthBar(player.getHP());
    private HealthBar enemyHP = new HealthBar(enemy.getHP());

    private final ImageButton attack = new ImageButton("Sprites/Attack0" + ".png", "Sprites/Attack1.png");
    private final ImageButton buff = new ImageButton("Sprites/Buff0" + ".png", "Sprites/Buff1.png");
    private final ImageButton heal = new ImageButton("Sprites/Heal0" + ".png", "Sprites/Heal1.png");
    private ImageButton[] battleButtons;
    private int battleIndex = 0;

    private enum BattleState {
        SELECTING,
        ATTACK,
        HEAL,
        BUFF,
        DODGING,
    }

    private BattleState state = BattleState.SELECTING;

    public BattleScene(Runnable onWin, Runnable onLose) {

        setLayout(new BorderLayout());
        setBackground(Color.black);

        battleButtons = new ImageButton[]{attack, buff, heal};

        Panel playerSection = new Panel(new GridBagLayout());

        GridBagConstraints gbc1 = new GridBagConstraints();

        gbc1.gridx = 0;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1.0;

        gbc1.gridy = 0;
        playerSection.add(playerHP, gbc1);

        gbc1.gridy = 1;
        playerSection.add(player, gbc1);

        gbc1.gridx = 1;
        playerSection.add(attackBar, gbc1);

        Panel enemySection = new Panel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.gridx = 0;
        gbc2.fill = GridBagConstraints.VERTICAL;
        gbc2.weightx = 1.0;

        gbc2.gridy = 0;
        enemySection.add(enemyHP, gbc2);

        gbc2.gridy = 1;
        enemySection.add(enemy, gbc2);

        boxCards = new CardLayout();
        boxSection = new Panel(boxCards);
        boxSection.setPreferredSize(new Dimension(400, 250));
        boxSection.add(battleBox, "BATTLE");
        boxSection.add(buffDialogue, "BUFF");
        boxSection.add(healDialogue, "HEAL");
        Panel centerWrapper = new Panel(new GridBagLayout());
        centerWrapper.add(boxSection);

        Panel buttonSection = new Panel(new FlowLayout(FlowLayout.CENTER,20,10));
        buttonSection.add(attack);
        buttonSection.add(buff);
        buttonSection.add(heal);

        add(buttonSection, BorderLayout.SOUTH);
        add(enemySection, BorderLayout.EAST);
        add(centerWrapper, BorderLayout.CENTER);
        add(playerSection,BorderLayout.WEST);

        actions = new Runnable[]{
                onWin,
                onLose
        };
    }

    @Override
    public void onEnter() {
        setState(BattleState.SELECTING);
        resetSelection();
    }

    @Override
    public void onExit() {}

    private void startAttack() {
        setState(BattleState.ATTACK);
        attackBar.start();
    }

    private void startHeal(){
        setState(BattleState.HEAL);
    }

    private void startBuff(){
        setState(BattleState.BUFF);
    }

    private void updateMovement() {
        float dx = 0;
        float dy = 0;

        if (left)  dx -= 1;
        if (right) dx += 1;
        if (up)    dy -= 1;
        if (down)  dy += 1;

        battleBox.setInput(dx, dy);
    }

    public void setState(BattleState newState) {
        state = newState;

        switch (state) {
            case DODGING -> {
                left = false;
                right = false;
                up = false;
                down = false;
                battleBox.setInput(0, 0);
                battleBox.centerSoul();
                battleBox.startWave(battleBox.getWidth(), battleBox.getHeight());
                attackBar.setVisibleBar(false);}
            case ATTACK -> attackBar.setVisibleBar(true);
            case BUFF -> {
                player.buff(2);
                showDialogueAndContinue("BUFF");}
            case HEAL -> {
                player.updateBuff();player.heal(20);
                playerHP.setHP(player.getHP());
                showDialogueAndContinue("HEAL");}
            case SELECTING -> {
                attackBar.setVisibleBar(false);
                buffDialogue.setVisible(false);
                healDialogue.setVisible(false);
            }
        }

        repaint();
    }

    @Override
    public void update() {

        switch (state) {

            case ATTACK -> {
                attackBar.update();
                if (attackBar.isFinished()) {

                    int damage = attackBar.calculateDamage(player, enemy);
                    enemy.takeDamage(damage);
                    enemyHP.setHP(enemy.getHP());

                    player.updateBuff();

                    if (enemy.getHP() <= 0) {
                        actions[0].run();
                    } else setState(BattleState.DODGING);
                }
            }

            case DODGING -> {
                battleBox.update();
                if (battleBox.pattern != null) {
                    int damage = battleBox.calculateDamage(enemy, player);
                    for (Rectangle hitBoxes : battleBox.pattern.getHitboxes()) {
                        if (hitBoxes.intersects(battleBox.soul.getBounds()) && !battleBox.soul.isInvincible()) {
                            player.takeDamage(damage);
                        }
                        if (battleBox.soul.collides(hitBoxes)) {
                            battleBox.soul.setInvincible(300);
                        }
                    }
                }
                playerHP.setHP(player.getHP());
                if (player.getHP() <=0){
                    actions[1].run();
                }
                if(battleBox.waveFinished()) {
                setState(BattleState.SELECTING);
                }
            }

            default -> {}
        }
    }

    private void showDialogueAndContinue(String card) {

        boxCards.show(boxSection, card);

        Timer timer = new Timer(1500, e -> {

            boxCards.show(boxSection, "BATTLE");
            setState(BattleState.DODGING);

        });

        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void handleKey(KeyEvent e) {
        if (state == BattleState.DODGING) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT  -> left = true;
                case KeyEvent.VK_RIGHT -> right = true;
                case KeyEvent.VK_UP    -> up = true;
                case KeyEvent.VK_DOWN  -> down = true;
            }
            updateMovement();
        }

        if (state == BattleState.SELECTING) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> moveSelection(-1);
                case KeyEvent.VK_RIGHT -> moveSelection(1);
                case KeyEvent.VK_Z, KeyEvent.VK_ENTER -> activate();
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {

        if (state != BattleState.DODGING) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_UP    -> up = false;
            case KeyEvent.VK_DOWN  -> down = false;
        }
        updateMovement();
    }

    protected void moveSelection(int dir) {

        battleButtons[battleIndex].setSelected(false);

        battleIndex += dir;

        if (battleIndex < 0) battleIndex = battleButtons.length - 1;
        if (battleIndex >= battleButtons.length) battleIndex = 0;

        battleButtons[battleIndex].setSelected(true);
    }

    protected void activate() {
        switch (battleIndex) {
            case 0 -> startAttack();
            case 1 -> startBuff();
            case 2 -> startHeal();
        }
    }

    protected void resetSelection() {
        for (ImageButton button : battleButtons) {
            button.setSelected(false);
        }

        battleIndex = 0;
        battleButtons[0].setSelected(true);
    }
}