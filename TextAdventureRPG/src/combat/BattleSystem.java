package combat;

import core.Player;
import core.Enemy;

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
            log.append("🎉 Victory!\n");

            player.gainExperience(50);

            log.append("⭐ You gained 50 XP!\n");
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