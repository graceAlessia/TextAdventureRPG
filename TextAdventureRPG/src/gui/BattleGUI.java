package gui;

import javax.swing.*;
import java.awt.*;

import core.Player;
import core.Enemy;
import combat.*;
import model.EnemyType;
import model.HeroType;

public class BattleGUI extends JFrame {

    private Player player;
    private Enemy enemy;
    private BattleSystem battleSystem;

    private JProgressBar playerHpBar;
    private JProgressBar enemyHpBar;
    private JProgressBar playerManaBar;
    private JProgressBar playerXpBar;

    private JLabel playerLabel;
    private JLabel enemyLabel;

    private JButton meleeButton;
    private JButton magicButton;
    private JButton rangedButton;
    private JButton restartButton;
    private JButton exitButton;

    private JPanel topPanel;

    public BattleGUI() {

        setTitle("RPG Battle");
        setSize(750, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(30, 30, 30));

        // ===== PLAYER SETUP =====
        String playerName = JOptionPane.showInputDialog(this, "Enter your hero name:");
        if (playerName == null || playerName.isBlank()) playerName = "Hero";

        HeroType selectedType = (HeroType) JOptionPane.showInputDialog(
                this,
                "Choose your hero type:",
                "Hero Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                HeroType.values(),
                HeroType.WARRIOR
        );

        if (selectedType == null) selectedType = HeroType.WARRIOR;

        player = new Player(playerName, selectedType);
        enemy = new Enemy("Goblin", EnemyType.GOBLIN, new MeleeAttack());
        battleSystem = new BattleSystem();

        // ===== TOP PANEL =====
        topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(new Color(45, 45, 45));

        playerLabel = new JLabel(player.getName() + " (" + player.getHeroType() + ")");
        playerLabel.setForeground(Color.WHITE);

        playerHpBar = new JProgressBar(0, player.getMaxHealth());
        playerHpBar.setStringPainted(true);
        playerHpBar.setForeground(new Color(46, 204, 113));

        playerManaBar = new JProgressBar(0, player.getMaxMana());
        playerManaBar.setStringPainted(true);
        playerManaBar.setForeground(new Color(52, 152, 219));

        playerXpBar = new JProgressBar(0, player.getRequiredXP());
        playerXpBar.setStringPainted(true);
        playerXpBar.setForeground(new Color(155, 89, 182));

        enemyLabel = new JLabel("Enemy: " + enemy.getName());
        enemyLabel.setForeground(Color.WHITE);

        enemyHpBar = new JProgressBar(0, enemy.getMaxHealth());
        enemyHpBar.setStringPainted(true);
        enemyHpBar.setForeground(new Color(231, 76, 60));

        topPanel.add(playerLabel);
        topPanel.add(playerHpBar);
//        topPanel.add(new JLabel("Mana"));
//        topPanel.add(playerManaBar);
//        topPanel.add(new JLabel("Experience"));
//        topPanel.add(playerXpBar)
        JLabel manaLabel = new JLabel("Mana");
        manaLabel.setForeground(Color.WHITE);

        JLabel xpLabel = new JLabel("Experience");
        xpLabel.setForeground(Color.WHITE);

        topPanel.add(manaLabel);
        topPanel.add(playerManaBar);
        topPanel.add(xpLabel);
        topPanel.add(playerXpBar);
        topPanel.add(enemyLabel);
        topPanel.add(enemyHpBar);

        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER LOG =====
        JTextArea battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        battleLog.setBackground(new Color(25, 25, 25));
        battleLog.setForeground(Color.WHITE);

        add(new JScrollPane(battleLog), BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(45, 45, 45));

        meleeButton = new JButton("Melee");
        magicButton = new JButton("Magic");
        rangedButton = new JButton("Ranged");
        restartButton = new JButton("Play Again");
        exitButton = new JButton("Exit");

        restartButton.setVisible(false);
        exitButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));

        bottomPanel.add(meleeButton);
        bottomPanel.add(magicButton);
        bottomPanel.add(rangedButton);
        bottomPanel.add(restartButton);
        bottomPanel.add(exitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        meleeButton.addActionListener(e ->
                handleAttack(new MeleeAttack(), battleLog));

        magicButton.addActionListener(e ->
                handleAttack(new MagicAttack(), battleLog));

        rangedButton.addActionListener(e ->
                handleAttack(new RangedAttack(), battleLog));

        updateBars();
        setVisible(true);
    }

    // ================= ATTACK =================
    private void handleAttack(AttackBehavior attack, JTextArea battleLog) {

        String result = battleSystem.performPlayerAttack(player, enemy, attack);
        battleLog.append(result + "\n");

        updateBars();

        // PLAYER DEAD
        if (!player.isAlive()) {
            disableButtons();
            restartButton.setVisible(true);
            exitButton.setVisible(true);
            return;
        }

        // ENEMY DEAD
        if (!enemy.isAlive()) {
/*
            int xpReward = switch (enemy.getType()) {
                case GOBLIN -> 40;
                case GOLEM -> 70;
                default -> 150;
            };

            player.gainExperience(xpReward);

            battleLog.append("⭐ Gained " + xpReward + " XP!\n");
            battleLog.append("Level: " + player.getLevel() + "\n");

            playerXpBar.setValue(player.getExperience());
            playerXpBar.setString("XP: " + player.getExperience() + " / 100");*/

            if (enemy.getType() == EnemyType.BOSS) {
                battleLog.append("\n🏆 YOU DEFEATED THE FINAL BOSS!\n");
                disableButtons();
                restartButton.setVisible(true);
                exitButton.setVisible(true);
                return;
            }

            chooseNewHero();

            if (player.getLevel() >= 3) {
                enemy = new Enemy("Boss", EnemyType.BOSS, new MeleeAttack());
                battleLog.append("\n🔥 The FINAL BOSS appears!\n");
            } else {
                enemy = new Enemy("Golem", EnemyType.GOLEM, new MeleeAttack());
                battleLog.append("\n🪨 A Golem appears!\n");
            }

            enemyLabel.setText("Enemy: " + enemy.getName());
            enemyHpBar.setMaximum(enemy.getMaxHealth());

            updateBars();
            enableButtons();
        }
    }

    // ================= UPDATE UI =================
    private void updateBars() {

        playerHpBar.setMaximum(player.getMaxHealth());
        playerHpBar.setValue(player.getHealth());
        playerHpBar.setString("HP: " + player.getHealth() + " / " + player.getMaxHealth());

        playerManaBar.setMaximum(player.getMaxMana());
        playerManaBar.setValue(player.getMana());
        playerManaBar.setString("Mana: " + player.getMana());

       // playerXpBar.setValue(player.getExperience());
        playerXpBar.setMaximum(player.getRequiredXP());
        playerXpBar.setValue(player.getExperience());
        playerXpBar.setString("XP: " + player.getExperience() 
            + " / " + player.getRequiredXP());

        enemyHpBar.setValue(enemy.getHealth());
        enemyHpBar.setString("HP: " + enemy.getHealth() + " / " + enemy.getMaxHealth());

        // Show mana only for Mage
        playerManaBar.setVisible(player.getHeroType() == HeroType.MAGE);

        playerLabel.setText(player.getName() + " (" + player.getHeroType() + ")");
    }

    private void disableButtons() {
        meleeButton.setEnabled(false);
        magicButton.setEnabled(false);
        rangedButton.setEnabled(false);
    }

    private void enableButtons() {
        meleeButton.setEnabled(true);
        magicButton.setEnabled(true);
        rangedButton.setEnabled(true);
    }

    // ================= HERO SWITCH =================
   /* private void chooseNewHero() {

        HeroType newType = (HeroType) JOptionPane.showInputDialog(
                this,
                "Choose your new hero type:",
                "Hero Change",
                JOptionPane.QUESTION_MESSAGE,
                null,
                HeroType.values(),
                player.getHeroType()
        );

        if (newType != null) {

            int level = player.getLevel();
            int xp = player.getExperience();
            String name = player.getName();

            player = new Player(name, newType);
            player.setLevel(level);
            player.setExperience(xp);

            updateBars();
        }
    }*/
    private void chooseNewHero() {

        HeroType newType = (HeroType) JOptionPane.showInputDialog(
                this,
                "Choose your new hero type:",
                "Hero Change",
                JOptionPane.QUESTION_MESSAGE,
                null,
                HeroType.values(),
                player.getHeroType()
        );

        if (newType != null) {

            int oldLevel = player.getLevel();
            int oldXP = player.getExperience();
            String name = player.getName();

            Player newPlayer = new Player(name, newType);

            // restore level properly
            for (int i = 1; i < oldLevel; i++) {
                newPlayer.gainExperience(newPlayer.getRequiredXP());
            }

            newPlayer.setExperience(oldXP);

            player = newPlayer;

            updateBars();
        }
    }

    // ================= RESTART =================
    private void restartGame() {

        player = new Player(player.getName(), player.getHeroType());
        enemy = new Enemy("Goblin", EnemyType.GOBLIN, new MeleeAttack());

        enemyLabel.setText("Enemy: " + enemy.getName());

        updateBars();

        enableButtons();
        restartButton.setVisible(false);
        exitButton.setVisible(false);
    }
}