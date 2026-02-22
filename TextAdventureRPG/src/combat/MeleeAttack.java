package combat;

import core.GameCharacter;
import core.Player;
import model.HeroType;

public class MeleeAttack implements AttackBehavior {

    @Override
    public void attack(GameCharacter attacker, GameCharacter target) {

        int baseDamage = 10;

        if (attacker instanceof Player) {
            Player player = (Player) attacker;

            if (player.getHeroType() == HeroType.WARRIOR) {
                baseDamage = 20; // warrior strong melee
            } else if (player.getHeroType() == HeroType.ARCHER) {
                baseDamage = 8;
            }
        }

        target.takeDamage(baseDamage);
    }
}