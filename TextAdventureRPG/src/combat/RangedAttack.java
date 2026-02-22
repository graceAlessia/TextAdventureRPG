package combat;

import core.GameCharacter;
import core.Player;
import model.HeroType;

public class RangedAttack implements AttackBehavior {

    @Override
    public void attack(GameCharacter attacker, GameCharacter target) {

        int baseDamage = 12;

        if (attacker instanceof Player) {
            Player player = (Player) attacker;

            if (player.getHeroType() == HeroType.ARCHER) {
                baseDamage = 22; // archer strong ranged
            } else if (player.getHeroType() == HeroType.WARRIOR) {
                baseDamage = 9;
            }
        }

        target.takeDamage(baseDamage);
    }
}