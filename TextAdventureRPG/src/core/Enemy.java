package core;

import combat.AttackBehavior;
import model.EnemyType;

public class Enemy extends GameCharacter {

    private EnemyType enemyType;
    private AttackBehavior attackBehavior;
  

    public Enemy(String name, EnemyType type, AttackBehavior attackBehavior) {
        super(name, 0);

        this.enemyType = type;
        this.attackBehavior = attackBehavior;

        switch (enemyType) {
            case GOBLIN:
                this.maxHealth = 90;
                break;
            case GOLEM:
                this.maxHealth = 150;
                break;
            case BOSS:
                this.maxHealth = 250;
                break;
        }

        this.health = this.maxHealth;
    }

    public int getMaxHealth() {
    	return maxHealth;
    }
    @Override
    public void attack(GameCharacter target) {
        attackBehavior.attack(this, target);
    }

    public EnemyType getType() {
        return enemyType;
    }
}
