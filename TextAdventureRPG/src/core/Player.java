package core;

import model.HeroType;



public class Player extends GameCharacter {

    private HeroType heroType;

    private int maxMana;
    private int mana;
    private int experience;

    
    //constructor
    public Player(String name, HeroType heroType) {
        super(name, 100); // temporary value, will override

        this.heroType = heroType;
        this.level = 1;
        this.experience = 0;

        // hero base stats 
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

        // Set starting values
        setHealth(maxHealth);
        mana = maxMana;
    }

    
    
    //getters 
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
    
    public int getExperience() {
        return experience;
    }


//    public int getLevel() {
//        return level;
//    }

//    public void setLevel(int level) {
//        this.level = level;
//    }

    //to restore hero after switching 
    public void setExperience(int experience) {
        this.experience = experience;
    }

    //spend-mana 
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

        while (experience >= getRequiredXP()) {
            experience -= getRequiredXP();
            levelUp();

//            maxHealth += 20;
//            health = maxHealth;
//
//            maxMana += 10;
//            mana = maxMana;
//
//            System.out.println("🔥 LEVEL UP! Now Level " + level);
        }
    }
    
    
    //XP calling formula
    public int getRequiredXP() {
    	return 100 * level;
    }

    /*
     * What happens when leveling up:
     * - Level increases
     * - Max HP increases
     * - Max Mana increases
     * - Fully heal
     * - Fully restore mana
     */
    
    private void levelUp() {
        level++;
        maxHealth += 25;
        maxMana += 25;

        setHealth(maxHealth);
        mana = maxMana;

        System.out.println("Level up! Now level " + level);
    }
    
   
    @Override
    public void attack(GameCharacter target) {
        // handled by BattleSystem
    }
}
