package Entities;

import java.awt.*;

public class Character extends Entity {
    private int health;
    private int attack;
    private final int baseAttack;
    private int defense;
    private int buffTurns=0;

    public Character(String name, String sprite, int health, int attack, int defense) {
        super(name, sprite);
        this.health = health;
        this.attack = attack;
        this.baseAttack = attack;
        this.defense = defense;
    }

    public void takeDamage(int damage){
        this.health -= damage;
    }

    public void buff(int turns){
        attack = baseAttack*2;
        buffTurns = turns;
    }

    public void updateBuff() {
        if (buffTurns > 0) {
            buffTurns--;

            if (buffTurns == 0) {
                attack = baseAttack;
            }
        }
    }

    public void heal(int heal) {
        this.health += heal;
    }

    public int getHP() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200,300);
    }
}
