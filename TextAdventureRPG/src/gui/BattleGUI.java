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
        

        // ===== Player Setup =====
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

        // ================= TOP PANEL =================
        topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(new Color(45, 45, 45));

       // JLabel playerLabel = new JLabel(player.getName() + " (" + player.getHeroType() + ")");
        playerLabel = new JLabel(player.getName() + " ( " + player.getHeroType() + " )");
        playerLabel.setForeground(Color.WHITE);

        playerHpBar = new JProgressBar(0, player.getMaxHealth());
        playerHpBar.setValue(player.getHealth());
        playerHpBar.setStringPainted(true);
        playerHpBar.setForeground(new Color(46, 204, 113));

        playerManaBar = new JProgressBar(0, player.getMaxMana());
        playerManaBar.setValue(player.getMana());
        playerManaBar.setStringPainted(true);
        playerManaBar.setForeground(new Color(52, 152, 219));

        playerXpBar = new JProgressBar(0, 100);
        playerXpBar.setValue(player.getExperience());
        playerXpBar.setStringPainted(true);
        playerXpBar.setForeground(new Color(155, 89, 182));

        enemyLabel = new JLabel("Enemy: " + enemy.getName());
        enemyLabel.setForeground(Color.WHITE);

        enemyHpBar = new JProgressBar(0, enemy.getMaxHealth());
        enemyHpBar.setValue(enemy.getHealth());
        enemyHpBar.setStringPainted(true);
        enemyHpBar.setForeground(new Color(231, 76, 60));

        topPanel.add(playerLabel);
        topPanel.add(playerHpBar);

       /* if (player.getHeroType() == HeroType.MAGE) {
            topPanel.add(new JLabel("Mana"));
            topPanel.add(playerManaBar);
        } else {
            topPanel.add(new JLabel(""));
            topPanel.add(new JLabel(""));
        }*/
        topPanel.add(new JLabel("Mana"));
        topPanel.add(playerManaBar);

        topPanel.add(new JLabel("Experience"));
        topPanel.add(playerXpBar);
        topPanel.add(enemyLabel);
        topPanel.add(enemyHpBar);

        add(topPanel, BorderLayout.NORTH);

        // ================= CENTER LOG =================
        JTextArea battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        battleLog.setBackground(new Color(25, 25, 25));
        battleLog.setForeground(Color.WHITE);

        add(new JScrollPane(battleLog), BorderLayout.CENTER);

        // ================= BOTTOM PANEL =================
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
        exitButton.addActionListener(e -> System.exit(0)); // can also use dispose();
    	

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
    
    	
    
  

    // ================= ATTACK HANDLER =================
    private void handleAttack(AttackBehavior attack, JTextArea battleLog) {

        String result = battleSystem.performPlayerAttack(player, enemy, attack);
        battleLog.append(result + "\n");

        updateBars();
        //Display Manabar for mage
        if (player.getHeroType() == HeroType.MAGE) {
            playerManaBar.setVisible(true);
        } else {
            playerManaBar.setVisible(false);
        }
        //playerManaBar.setEnabled(player.getHeroType() == HeroType.MAGE);

        // PLAYER DEAD
        if (!player.isAlive()) {
            battleLog.append("\n💀 You were defeated!\n");
            disableButtons();
            restartButton.setVisible(true);
            exitButton.setVisible(true);
            return;
        }

        // ENEMY DEAD
        if (!enemy.isAlive()) {

            int xpReward;

            if (enemy.getType() == EnemyType.GOBLIN) {
                xpReward = 40;
            } else if (enemy.getType() == EnemyType.GOLEM) {
                xpReward = 70;
            } else {
                xpReward = 150;
            }

            player.gainExperience(xpReward);

            playerXpBar.setMaximum(100);
            playerXpBar.setValue(player.getExperience());
            playerXpBar.setString("XP: " + player.getExperience() + " / 100");

            battleLog.append("⭐ Gained " + xpReward + " XP!\n");
            battleLog.append("Level: " + player.getLevel() + "\n");

            // Boss defeated
            if (enemy.getType() == EnemyType.BOSS) {
                battleLog.append("\n🏆 YOU DEFEATED THE FINAL BOSS!\n");
                battleLog.append("🎉 GAME COMPLETED!\n");
                disableButtons();
                restartButton.setVisible(true);
                exitButton.setVisible(true);
                return;
            }

            chooseNewHero();
            
            topPanel.revalidate();
            topPanel.repaint();

            // Spawn next enemy
            if (player.getLevel() >= 3) {
                enemy = new Enemy("Boss", EnemyType.BOSS, new MeleeAttack());
                battleLog.append("\n🔥 The FINAL BOSS appears!\n");
            } else {
                enemy = new Enemy("Golem", EnemyType.GOLEM, new MeleeAttack());
                battleLog.append("\n🪨 A Golem appears!\n");
            }

            enemyLabel.setText("Enemy: " + enemy.getName());
            enemyHpBar.setMaximum(enemy.getMaxHealth());
            enemyHpBar.setValue(enemy.getHealth());

            enemyHpBar.setString("HP: " + enemy.getHealth()	 + " / " + enemy.getMaxHealth()); 
            enableButtons();
        }
    }

    
    //============= Update Bar =======================
    private void updateBars() {
        playerHpBar.setValue(player.getHealth());
        playerHpBar.setString("HP: " + player.getHealth() + " / " + player.getMaxHealth());

        playerManaBar.setValue(player.getMana());
        playerManaBar.setString("Mana: " + player.getMana());

        enemyHpBar.setValue(enemy.getHealth());
        enemyHpBar.setString("HP: " + enemy.getHealth() + " / " + enemy.getMaxHealth());
   
        if (player.getHeroType() == HeroType.MAGE) {
            playerManaBar.setVisible(true);
        } else {
            playerManaBar.setVisible(false);
        }
    
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

            int currentLevel = player.getLevel();
            int currentXP = player.getExperience();
            String name = player.getName();

            player = new Player(name, newType);

            player.setLevel(currentLevel);
            player.setExperience(currentXP);

//            playerHpBar.setMaximum(player.getMaxHealth());
//            updateBars();
        
            playerHpBar.setMaximum(player.getMaxHealth());
            playerHpBar.setValue(player.getHealth());
            playerHpBar.setString("HP: " + player.getHealth() + " / " + player.getMaxHealth());
            
            playerManaBar.setMaximum(player.getMaxMana());
            playerManaBar.setValue(player.getMana());
            playerManaBar.setString("Mana: " + player.getMana());
            
            playerXpBar.setValue(player.getExperience());
            playerXpBar.setString("XP: " + player.getExperience() + " / 100");
            
            playerLabel.setText(player.getName() + " (" + player.getHeroType() + ")");
            
            
        }
        if (player.getHeroType() == HeroType.MAGE) {
            playerManaBar.setVisible(true);
        } else {
            playerManaBar.setVisible(false);
        }
        
        
    }
    
    
    //restartGame() Method
    
    private void restartGame() {
    	// Reset player completely
    	player = new Player(player.getName(), player.getHeroType());
    	
    	//Reset enemy to Goblin
    	enemy = new Enemy("Goblin", EnemyType.GOBLIN, new MeleeAttack());
    	
    	enemyLabel.setText("Enemy: " + enemy.getName());
    	
    	//Reset bars
    	playerHpBar.setMaximum(player.getMaxHealth());
    	playerManaBar.setMaximum(player.getMaxMana());
    	playerXpBar.setValue(0);
    	playerXpBar.setString("XP: 0 / 100");
    	enemyHpBar.setMaximum(enemy.getMaxHealth());
    	
    	updateBars();
    	
    	//Enable attack Buttons
    	enableButtons();
    	
    	//Hide restart/exit
    	restartButton.setVisible(false);
    	exitButton.setVisible(false);
    
    }
    
}