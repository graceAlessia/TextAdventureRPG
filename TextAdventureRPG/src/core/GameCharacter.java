package core;


//Both player and enemy inherit from this 

public abstract class GameCharacter {

    protected String name;
    protected int health;
    protected int maxHealth;
    protected int level;
    

    
    // Constructor
    public GameCharacter(String name, int health) {
        this.name = name;
        this.health = health;
        this.level = 1;
    }

    // check if character is alive
    public boolean isAlive() {
        return health > 0;
    }

    
    //reduce HP when taking damage
    public void takeDamage(int damage) {
        health -= damage;
        
        //prevent - HP
        if (health < 0) health = 0;
    }

    
    //Getters & Setters 

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
   
    // for restoring health or leveling up
    public void setHealth(int health) {
    	this.health = health;
    }

    
    public int getLevel() {
        return level;
    }
    
    //restore hero after switching 
    public void setLevel(int level) {
        this.level = level;
    }

    
    public abstract void attack(GameCharacter target);
}
