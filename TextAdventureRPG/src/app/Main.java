package app;

import core.Player;
import core.Enemy;
import combat.BattleSystem;
import combat.RangedAttack;
import model.EnemyType;

public class Main {

    public static void main(String[] args) {

        Player player = new Player("Hero");

        Enemy enemy = new Enemy(
                "Goblin",
                EnemyType.GOBLIN,
                new RangedAttack()
        );

        BattleSystem battleSystem = new BattleSystem();
        battleSystem.startBattle(player, enemy);
    }
}
