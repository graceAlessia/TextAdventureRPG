package combat;

import core.GameCharacter;

public class MeleeAttack implements AttackBehavior {

    @Override
    public void attack(GameCharacter attacker, GameCharacter target) {
        int damage = 10 + attacker.getLevel() * 2;
        target.takeDamage(damage);
        System.out.println(attacker.getName() + " hits for " + damage + " damage.");
    }
}
