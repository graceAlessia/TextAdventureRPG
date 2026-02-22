package core;

import model.HeroType;

public class Player extends GameCharacter {

    private HeroType heroType;

    private int maxHealth;
    private int maxMana;
    private int mana;
    private int health;
    private int level;
    private int experience;

    public Player(String name, HeroType heroType) {
        super(name, 100); // temporary value, will override

        this.heroType = heroType;
        this.level = 1;
        this.experience = 0;

        switch (heroType) {

            case WARRIOR:
                maxHealth = 150;
                maxMana = 20;
                break;

            case ARCHER:
                maxHealth = 110;
                maxMana = 40;
                break;

            case MAGE:
                maxHealth = 90;
                maxMana = 120;
                break;
        }

        // Set actual values
        setHealth(maxHealth);
        mana = maxMana;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean useMana(int amount) {
        if (mana >= amount) {
            mana -= amount;
            return true;
        }
        return false;
    }

   /* public void gainExperience(int exp) {
        experience += exp;

        if (experience >= 100) {
            levelUp();
            experience = 0;
        }
    }*/
    public void gainExperience(int xp) {

        experience += xp;

        while (experience >= 100) {
            experience -= 100;
            level++;

            // Optional: Increase stats on level up
            maxHealth += 20;
            health = maxHealth;

            maxMana += 10;
            mana = maxMana;

            System.out.println("LEVEL UP! Now Level " + level);
        }
    }

    private void levelUp() {
        level++;

        maxHealth += 25;
        maxMana += 15;

        setHealth(maxHealth);
        mana = maxMana;

        System.out.println("Level up! Now level " + level);
    }
    
    public int getExperience() {
        return experience;
    }

    @Override
    public void attack(GameCharacter target) {
        // handled by BattleSystem
    }
}
