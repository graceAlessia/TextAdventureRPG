package core;

public class Player extends GameCharacter {

    private int experience;

    public Player(String name) {
        super(name, 100);
        this.experience = 0;
    }

    @Override
    public void attack(GameCharacter target) {
        // actual attack handled by BattleSystem
    }

    public void gainExperience(int exp) {
        experience += exp;
        if (experience >= 100) {
            levelUp();
            experience = 0;
        }
    }

    private void levelUp() {
        level++;
        health += 20;
        System.out.println("⬆ " + name + " leveled up to level " + level);
    }
}
