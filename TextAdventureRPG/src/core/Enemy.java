package core;

import combat.AttackBehavior;
import model.EnemyType;

public class Enemy extends GameCharacter {

    private EnemyType type;
    private AttackBehavior attackBehavior;

    public Enemy(String name, EnemyType type, AttackBehavior attackBehavior) {
        super(name, 80);
        this.type = type;
        this.attackBehavior = attackBehavior;
    }

    @Override
    public void attack(GameCharacter target) {
        attackBehavior.attack(this, target);
    }

    public EnemyType getType() {
        return type;
    }
}
