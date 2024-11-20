package model;

import java.util.Random;

public class Battle {
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon activePokemon;
    private Pokemon opponentPokemon;
    private Random random = new Random();

    public Battle(Pokemon pokemon1, Pokemon pokemon2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;

        // Set the Pokémon with higher speed as the first attacker
        if (pokemon1.getStatus().getSpeed() >= pokemon2.getStatus().getSpeed()) {
            this.activePokemon = pokemon1;
            this.opponentPokemon = pokemon2;
        } else {
            this.activePokemon = pokemon2;
            this.opponentPokemon = pokemon1;
        }
    }

    public void startBattle() {
        System.out.println("Battle started between " + pokemon1.getName() + " and " + pokemon2.getName());
        while (pokemon1.getStatus().getHp() > 0 && pokemon2.getStatus().getHp() > 0) {
            takeTurn();
            if (opponentPokemon.getStatus().getHp() > 0) {
                switchTurns(); // Switch turns only if the opponent is still alive
            }
        }
        declareWinner();
    }

    private void takeTurn() {
        System.out.println(activePokemon.getName() + "'s turn!");

        Move chosenMove;

        if (activePokemon == opponentPokemon) {
            // Sử dụng MiniMax để chọn chiêu tốt nhất cho đối thủ
            Node root = new Node(opponentPokemon, activePokemon);
            Tree tree = new Tree(root);
            tree.generateChildren(root, opponentPokemon.getMoves());

            MiniMax minimax = new MiniMax();
            chosenMove = minimax.getBestMove(root, 3, true); // Sâu 3 cấp
        } else {
            // Người chơi chọn chiêu ngẫu nhiên
            chosenMove = activePokemon.getMoves().get(random.nextInt(activePokemon.getMoves().size()));
        }

        // Hiển thị chiêu được chọn
        System.out.println(activePokemon.getName() + " used " + chosenMove.getNAME() + "!");

        // Thực thi chiêu thức
        if (chosenMove.getPOWER() > 0) {
            int damage = calculateDamage(chosenMove, activePokemon, opponentPokemon);
            applyDamage(opponentPokemon, damage);
        } else {
            applyStatusEffect(chosenMove);
        }
    }


    public int calculateDamage(Move move, Pokemon attacker, Pokemon defender) {
        // 1. Lấy các chỉ số cơ bản
        int power = move.getPOWER();
        int attack, defense;

        // Xác định Attack và Defense dựa trên loại chiêu (Physical hoặc Special)
        if (move.getMOVE() == MoveType.PHYSICAL) {
            attack = attacker.getStatus().getAtk(); // Dùng Attack
            defense = defender.getStatus().getDefense(); // Dùng Defense
        } else if (move.getMOVE() == MoveType.SPECIAL) {
            attack = attacker.getStatus().getSp_atk(); // Dùng Special Attack
            defense = defender.getStatus().getSp_defense(); // Dùng Special Defense
        } else {
            // Nếu là chiêu STATUS thì không gây sát thương
            return 0;
        }

        // 2. Modifier - Khắc Hệ (Type Effectiveness)
        double typeEffectiveness = move.getTYPE().getEffectiveness(defender.getType());

        // 3. Modifier - STAB (Same-Type Attack Bonus)
        double stab = (move.getTYPE() == attacker.getType()) ? 1.5 : 1.0;

        // 4. Modifier - Buff/Debuff (Chỉ số có thể bị tăng hoặc giảm)
        attack = adjustStatWithStage(attack, attacker.getBuffStage("Attack"));
        defense = adjustStatWithStage(defense, defender.getBuffStage("Defense"));

        // 5. Công thức tính sát thương cơ bản
        int level = attacker.getLevel();
        double baseDamage = (((2.0 * level / 5 + 2) * power * attack / defense) / 50) + 2;

        // 6. Modifier - Tổng hợp các yếu tố
        double modifier = typeEffectiveness * stab; // Bỏ qua random và critical hit như yêu cầu
        int totalDamage = (int) (baseDamage * modifier);

        // Đảm bảo sát thương không nhỏ hơn 1
        return Math.max(totalDamage, 1);
    }

    private int adjustStatWithStage(int baseStat, int stage) {
        if (stage > 0) {
            return (int) (baseStat * (2.0 + stage) / 2);
        } else if (stage < 0) {
            return (int) (baseStat * 2 / (2.0 - stage));
        }
        return baseStat;
    }

    private void applyDamage(Pokemon defender, int damage) {
        int currentHp = defender.getStatus().getHp();
        defender.getStatus().setHp(Math.max(currentHp - damage, 0)); // Ensure HP doesn't go below 0
        System.out.println(defender.getName() + " now has " + defender.getStatus().getHp() + " HP left.");
    }

    private void applyStatusEffect(Move move) {
        String moveName = move.getNAME();
        Status status = activePokemon.getStatus();

        switch (moveName) {
            case "Growl":
                opponentPokemon.getStatus().setAtk(Math.max(0, opponentPokemon.getStatus().getAtk() - 10));
                System.out.println(opponentPokemon.getName() + "'s Attack decreased!");
                break;
            case "Tail Whip":
                opponentPokemon.getStatus().setDefense(Math.max(0, opponentPokemon.getStatus().getDefense() - 10));
                System.out.println(opponentPokemon.getName() + "'s Defense decreased!");
                break;
            case "Swords Dance":
                status.setAtk(status.getAtk() + 20);
                System.out.println(activePokemon.getName() + "'s Attack increased sharply!");
                break;
            case "Withdraw":
                status.setDefense(status.getDefense() + 10);
                System.out.println(activePokemon.getName() + "'s Defense increased!");
                break;
            case "Growth":
                status.setAtk(status.getAtk() + 10);
                status.setSp_atk(status.getSp_atk() + 10);
                System.out.println(activePokemon.getName() + "'s Attack and Special Attack increased!");
                break;
            default:
                System.out.println(moveName + " has no effect.");
                break;
        }
    }

    private void switchTurns() {
        // Switch the active and opponent Pokémon
        Pokemon temp = activePokemon;
        activePokemon = opponentPokemon;
        opponentPokemon = temp;
    }

    private void declareWinner() {
        if (pokemon1.getStatus().getHp() <= 0 && pokemon2.getStatus().getHp() > 0) {
            System.out.println(pokemon2.getName() + " wins the battle!");
        } else if (pokemon2.getStatus().getHp() <= 0 && pokemon1.getStatus().getHp() > 0) {
            System.out.println(pokemon1.getName() + " wins the battle!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}
