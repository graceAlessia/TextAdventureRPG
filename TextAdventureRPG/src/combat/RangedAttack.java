package combat;

import core.GameCharacter;

public class RangedAttack implements AttackBehavior {

    @Override
    public void attack(GameCharacter attacker, GameCharacter target) {
        int damage = 12 + attacker.getLevel() * 2;
        target.takeDamage(damage);
        System.out.println(attacker.getName() + " shoots for " + damage + " damage.");
    }
}
