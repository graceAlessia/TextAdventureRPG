package example;

public class Enemy extends GameCharacter{

	public Enemy (String name) {
		super(name, 80);
		
	}
	
	public void attack(GameCharacter target) {
		target.takeDamage(8);
		System.out.println(name + "attacks!");
	}
}
