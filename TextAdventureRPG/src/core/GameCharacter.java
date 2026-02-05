package core;

public abstract class GameCharacter {

    protected String name;
    protected int health;
    protected int level;

    public GameCharacter(String name, int health) {
        this.name = name;
        this.health = health;
        this.level = 1;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public abstract void attack(GameCharacter target);

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }
}
