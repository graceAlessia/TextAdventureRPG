package combat;

import core.Player;
import core.Enemy;
import core.GameCharacter;

import java.util.Scanner;

public class BattleSystem {

    private Scanner scanner = new Scanner(System.in);

    public void startBattle(Player player, Enemy enemy) {

        System.out.println("\n⚔️ A wild " + enemy.getName() + " appears!\n");

        while (player.isAlive() && enemy.isAlive()) {

            playerTurn(player, enemy);

            if (enemy.isAlive()) {
                enemy.attack(player);
            }

            System.out.println("\n" + player.getName() + " HP: " + player.getHealth());
            System.out.println(enemy.getName() + " HP: " + enemy.getHealth());
            System.out.println("----------------------------------");
        }

        if (player.isAlive()) {
            System.out.println("🏆 Victory!");
            player.gainExperience(50);
        } else {
            System.out.println("💀 You were defeated...");
        }
    }

    private void playerTurn(Player player, GameCharacter enemy) {

        System.out.println("Choose your attack:");
        System.out.println("1. Melee");
        System.out.println("2. Magic");
        System.out.println("3. Ranged");

        int choice = scanner.nextInt();

        AttackBehavior attack;

        switch (choice) {
            case 2:
                attack = new MagicAttack();
                break;
            case 3:
                attack = new RangedAttack();
                break;
            case 1:
            default:
                attack = new MeleeAttack();
                break;
        }

        attack.attack(player, enemy);
    }
}
