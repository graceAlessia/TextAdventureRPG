package combat;

import core.Player;
import core.Enemy;
import model.EnemyType;

public class BattleSystem {

    public String performPlayerAttack(Player player,
                                      Enemy enemy,
                                      AttackBehavior attack) {

        StringBuilder log = new StringBuilder();

        // ===== PLAYER TURN =====
        attack.attack(player, enemy);

        log.append(player.getName())
           .append(" attacks ")
           .append(enemy.getName())
           .append("!\n");

        log.append(enemy.getName())
           .append(" HP: ")
           .append(enemy.getHealth())
           .append("\n");

        // ===== CHECK ENEMY DEAD =====
        if (!enemy.isAlive()) {

            int xpReward = switch (enemy.getType()) {
                case GOBLIN -> 40;
                case GOLEM -> 70; 
                case BOSS -> 150;
            };

            player.gainExperience(xpReward);

            log.append("🎉 Victory!\n");
            log.append("⭐ Gained ")
               .append(xpReward)
               .append(" XP!\n");

            log.append("Level: ")
               .append(player.getLevel())
               .append("\n");

            return log.toString();
        }

        // ===== ENEMY TURN =====
        enemy.attack(player);

        log.append(enemy.getName())
           .append(" attacks back!\n");

        log.append(player.getName())
           .append(" HP: ")
           .append(player.getHealth())
           .append("\n");

        // ===== CHECK PLAYER DEAD =====
        if (!player.isAlive()) {
            log.append("💀 You were defeated!\n");
        }

        return log.toString();
    }
}