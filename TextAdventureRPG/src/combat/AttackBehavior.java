package combat;

import core.GameCharacter;

public interface AttackBehavior {
    void attack(GameCharacter attacker, GameCharacter target);
}
