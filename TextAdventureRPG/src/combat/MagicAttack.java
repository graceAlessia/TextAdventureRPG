package combat;

import core.GameCharacter;

public class MagicAttack implements AttackBehavior {

    @Override
    public void attack(GameCharacter attacker, GameCharacter target) {
        int damage = 15 + attacker.getLevel() * 3;
        target.takeDamage(damage);
        System.out.println(attacker.getName() + " casts magic for " + damage + " damage.");
    }
}
