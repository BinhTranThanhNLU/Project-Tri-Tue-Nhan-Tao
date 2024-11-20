package view;

import controller.GameController;
import model.Battle;
import model.Move;
import model.MoveType;
import model.Pokemon;
import model.Status;
import model.TypePokemon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BattleScreen extends JFrame {
    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;
    private JLabel playerPokemonLabel;
    private JLabel opponentPokemonLabel;
    private JProgressBar playerHealthBar;
    private JProgressBar opponentHealthBar;
    private ArrayList<JButton> moveButtons = new ArrayList<>();
    private JTextArea battleLog;
    private boolean playerTurn = true; // Track whose turn it is
    private GameController controller;
    private boolean battleEnded = false;
    private JLabel playerBuffLabel;
    private JLabel opponentBuffLabel;



    public BattleScreen(GameController controller, Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.controller = controller;
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        setTitle("Pokemon Battle");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        setupBackground();
        setupUI();
        startBattle();
    }

    private void setupBackground() {
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/back3.png"));
        Image bgImage = bgIcon.getImage().getScaledInstance(850, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 850, 600);
        setContentPane(background);
        setLayout(null);
    }

    private void setupUI() {
    	playerPokemonLabel = new JLabel(playerPokemon.getName() + " Lvl " + playerPokemon.getLevel());
    	playerPokemonLabel.setForeground(Color.WHITE);
    	playerPokemonLabel.setBounds(50, 300, 150, 30);
    	add(playerPokemonLabel);

        playerHealthBar = createHealthBar(playerPokemon.getStatus());
        playerHealthBar.setBounds(50, 330, 150, 10);
        add(playerHealthBar);

        JLabel playerPokemonImage = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(playerPokemon.getImage())).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        playerPokemonImage.setBounds(50, 350, 100, 100);
        add(playerPokemonImage);

        opponentPokemonLabel = new JLabel(opponentPokemon.getName() + " Lvl " + opponentPokemon.getLevel());
        opponentPokemonLabel.setForeground(Color.WHITE);
        opponentPokemonLabel.setBounds(600, 50, 150, 30);
        add(opponentPokemonLabel);

        opponentHealthBar = createHealthBar(opponentPokemon.getStatus());
        opponentHealthBar.setBounds(600, 80, 150, 10);
        add(opponentHealthBar);

        JLabel opponentPokemonImage = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(opponentPokemon.getImage())).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        opponentPokemonImage.setBounds(600, 100, 100, 100);
        add(opponentPokemonImage);

        JPanel actionPanel = new JPanel(null);
        actionPanel.setBounds(0, 450, 850, 150);
        actionPanel.setBackground(Color.DARK_GRAY);

        JPanel movesPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        movesPanel.setOpaque(false);
        movesPanel.setBounds(600, 10, 180, 80);
        
        // Buff/Debuff label for player Pokémon
        playerBuffLabel = new JLabel("");
        playerBuffLabel.setForeground(Color.CYAN);
        playerBuffLabel.setBounds(50, 340, 200, 20); // Đẩy xuống thấp hơn (tăng y từ 350 lên 370)
        add(playerBuffLabel);

        // Buff/Debuff label for opponent Pokémon
        opponentBuffLabel = new JLabel("");
        opponentBuffLabel.setForeground(Color.CYAN);
        opponentBuffLabel.setBounds(600, 100, 200, 20); // Đẩy xuống thấp hơn (tăng y từ 130 lên 150)
        add(opponentBuffLabel);


        for (Move move : playerPokemon.getMoves()) {
            JButton moveButton = new JButton(move.getNAME());
            moveButton.addActionListener(e -> handlePlayerMove(move));
            moveButtons.add(moveButton);
            movesPanel.add(moveButton);
        }

        actionPanel.add(movesPanel);

        // Battle log to display actions and status updates
        battleLog = new JTextArea();
        battleLog.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font size
        battleLog.setEditable(false);
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);
        battleLog.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(battleLog);
        scrollPane.setBounds(10, 10, 550, 130);
        actionPanel.add(scrollPane);

        add(actionPanel);
    }

    private JProgressBar createHealthBar(Status status) {
        JProgressBar healthBar = new JProgressBar(0, status.getHp());
        healthBar.setValue(status.getHp());
        healthBar.setForeground(Color.RED);
        healthBar.setBackground(Color.DARK_GRAY);
        return healthBar;
    }

    private void handlePlayerMove(Move move) {
        if (playerTurn) {
            if (move.getMOVE() == MoveType.STATUS) {
                // Gọi applyStatusEffect với nhãn đúng
                applyStatusEffect(move, playerPokemon, opponentPokemon, playerBuffLabel, opponentBuffLabel);
            } else {
                // Tính sát thương và áp dụng
                int damage = calculateDamage(move, playerPokemon, opponentPokemon);
                opponentPokemon.getStatus().setHp(Math.max(opponentPokemon.getStatus().getHp() - damage, 0));
                logBattleMessage(playerPokemon.getName() + " used " + move.getNAME() + "!");
                logBattleMessage("It dealt " + damage + " damage!");
                logBattleMessage(getEffectivenessMessage(move, playerPokemon, opponentPokemon));
            }

            updateHealthBars();
            checkBattleStatus();
            playerTurn = false;

            // Trigger opponent's move after a short delay
            Timer timer = new Timer(1000, e -> handleOpponentMove());
            timer.setRepeats(false);
            timer.start();
        }
    }



    private void handleOpponentMove() {
        if (!playerTurn) {
            Move move = getRandomMove(opponentPokemon);
            if (move.getMOVE() == MoveType.STATUS) {
                // Gọi applyStatusEffect với nhãn đúng
                applyStatusEffect(move, opponentPokemon, playerPokemon, opponentBuffLabel, playerBuffLabel);
            } else {
                // Tính sát thương và áp dụng
                int damage = calculateDamage(move, opponentPokemon, playerPokemon);
                playerPokemon.getStatus().setHp(Math.max(playerPokemon.getStatus().getHp() - damage, 0));
                logBattleMessage(opponentPokemon.getName() + " used " + move.getNAME() + "!");
                logBattleMessage("It dealt " + damage + " damage!");
                logBattleMessage(getEffectivenessMessage(move, opponentPokemon, playerPokemon));
            }

            updateHealthBars();
            checkBattleStatus();
            playerTurn = true;
        }
    }

    
    private void applyStatusEffect(Move move, Pokemon source, Pokemon target, JLabel sourceBuffLabel, JLabel targetBuffLabel) {
        String moveName = move.getNAME();
        logBattleMessage(source.getName() + " used " + moveName + "!");

        switch (moveName) {
            case "Growl":
                target.setBuffStage("Attack", target.getBuffStage("Attack") - 1);
                logBattleMessage(target.getName() + "'s Attack fell!");
                updateBuffLabel(target, targetBuffLabel); // Cập nhật nhãn của đối thủ
                break;
            case "Tail Whip":
                target.setBuffStage("Defense", target.getBuffStage("Defense") - 1);
                logBattleMessage(target.getName() + "'s Defense fell!");
                updateBuffLabel(target, targetBuffLabel); // Cập nhật nhãn của đối thủ
                break;
            case "Swords Dance":
                source.setBuffStage("Attack", source.getBuffStage("Attack") + 2);
                logBattleMessage(source.getName() + "'s Attack sharply rose!");
                updateBuffLabel(source, sourceBuffLabel); // Cập nhật nhãn của người dùng
                break;
            case "Withdraw":
                source.setBuffStage("Defense", source.getBuffStage("Defense") + 1);
                logBattleMessage(source.getName() + "'s Defense rose!");
                updateBuffLabel(source, sourceBuffLabel); // Cập nhật nhãn của người dùng
                break;
            case "Growth":
                source.setBuffStage("Attack", source.getBuffStage("Attack") + 1);
                source.setBuffStage("SpecialAttack", source.getBuffStage("SpecialAttack") + 1);
                logBattleMessage(source.getName() + "'s Attack and Special Attack rose!");
                updateBuffLabel(source, sourceBuffLabel); // Cập nhật nhãn của người dùng
                break;
            default:
                logBattleMessage(moveName + " had no effect.");
                return;
        }
    }


    
    private void updateBuffLabel(Pokemon pokemon, JLabel buffLabel) {
        StringBuilder buffs = new StringBuilder("<html>");
        int attackStage = pokemon.getBuffStage("Attack");
        int defenseStage = pokemon.getBuffStage("Defense");
        int spAttackStage = pokemon.getBuffStage("SpecialAttack");
        int spDefenseStage = pokemon.getBuffStage("SpecialDefense");
        int speedStage = pokemon.getBuffStage("Speed");

        if (attackStage != 0) {
            buffs.append("ATK: ").append(attackStage > 0 ? "+" : "").append(attackStage).append("<br>");
        }
        if (defenseStage != 0) {
            buffs.append("DEF: ").append(defenseStage > 0 ? "+" : "").append(defenseStage).append("<br>");
        }
        if (spAttackStage != 0) {
            buffs.append("SP.ATK: ").append(spAttackStage > 0 ? "+" : "").append(spAttackStage).append("<br>");
        }
        if (spDefenseStage != 0) {
            buffs.append("SP.DEF: ").append(spDefenseStage > 0 ? "+" : "").append(spDefenseStage).append("<br>");
        }
        if (speedStage != 0) {
            buffs.append("SPD: ").append(speedStage > 0 ? "+" : "").append(speedStage).append("<br>");
        }
        buffs.append("</html>");
        buffLabel.setText(buffs.toString());
    }



    private int calculateDamage(Move move, Pokemon attacker, Pokemon defender) {
    	Battle battle = controller.getBattle();
        return battle.calculateDamage(move, attacker, defender);
    }

    
    private String getEffectivenessMessage(Move move, Pokemon attacker, Pokemon defender) {
        double effectiveness = attacker.getType().getEffectiveness(defender.getType());
        TypePokemon moveType = move.getTYPE();

        // Kiểm tra điều kiện với chiêu hệ Thường
        if (moveType == TypePokemon.NORMAL) {
            if (effectiveness == 0.0) {
                return "It had no effect.";
            } else {
                return "It's effective.";
            }
        }

        // Logic thông thường cho các hệ khác
        if (effectiveness == 2.0) {
            return "It's super effective!";
        } else if (effectiveness == 0.5) {
            return "It's not very effective...";
        } else if (effectiveness == 0.0) {
            return "It had no effect.";
        } else {
            return "It's effective.";
        }
    }

    private Move getRandomMove(Pokemon pokemon) {
        ArrayList<Move> moves = pokemon.getMoves();
        if (moves == null || moves.isEmpty()) {
            throw new IllegalStateException("Pokemon " + pokemon.getName() + " has no available moves.");
        }
        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }


    private void updateHealthBars() {
        playerHealthBar.setValue(playerPokemon.getStatus().getHp());
        opponentHealthBar.setValue(opponentPokemon.getStatus().getHp());
    }

    private void logBattleMessage(String message) {
        battleLog.append(message + "\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength());
    }


    private void checkBattleStatus() {
        if (battleEnded) return; 

        if (playerPokemon.getStatus().getHp() <= 0) {
            logBattleMessage(playerPokemon.getName() + " fainted! You lost the battle.");
            endBattle();
        } else if (opponentPokemon.getStatus().getHp() <= 0) {
            logBattleMessage(opponentPokemon.getName() + " fainted! You won the battle.");
            endBattle();
        }
    }


    private void startBattle() {
        logBattleMessage("The battle between " + playerPokemon.getName() + " and " + opponentPokemon.getName() + " has begun!");
    }
    
    private void endBattle() {
        if (battleEnded) return; 
        battleEnded = true;

        for (JButton button : moveButtons) {
            button.setEnabled(false);
        }

        int score = playerPokemon.getStatus().getHp() > 0 ? 100 : 0; // Example score, adjust as needed
        controller.showGameOverScreen(score);
        this.dispose();
    }


}
