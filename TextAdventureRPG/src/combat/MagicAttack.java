package combat;

import core.GameCharacter;
import core.Player;
import model.HeroType;

public class MagicAttack implements AttackBehavior {

    @Override
    public void attack(GameCharacter attacker, GameCharacter target) {
    	
        if (attacker instanceof Player) {

            Player player = (Player) attacker;
            
            System.out.println("Remaining mana: " + player.getMana());
            
            if (player.getHeroType() != HeroType.MAGE) {
                target.takeDamage(5); // weak magic for non-mage
                return;
            }

            if (!player.useMana(10)) {
                // not enough mana
                return;
            }

            int damage = 25 + player.getLevel() * 3;
            target.takeDamage(damage);
            
            System.out.println("Remaining mana after cast: " + player.getMana());

            
        }
    
    }
   
}