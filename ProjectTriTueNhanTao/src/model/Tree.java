package model;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    // Tạo các trạng thái kế tiếp cho một node
    public void generateChildren(Node node, List<Move> moves) {
        List<Node> children = new ArrayList<>();
        for (Move move : moves) {
            Pokemon activeClone = (Pokemon) node.getActivePokemon().clone(); // Tạo bản sao Pokémon
            Pokemon opponentClone = (Pokemon) node.getOpponentPokemon().clone();

            // Áp dụng chiêu thức để tạo trạng thái mới
            int damage = calculateDamage(move, activeClone, opponentClone);
            opponentClone.getStatus().setHp(opponentClone.getStatus().getHp() - damage);

            // Thêm node mới
            children.add(new Node(opponentClone, activeClone));
        }
        node.setChildren(children);
    }

    // Tính sát thương (sử dụng logic từ lớp Battle)
    private int calculateDamage(Move move, Pokemon attacker, Pokemon defender) {
        int power = move.getPOWER();
        int attack = attacker.getStatus().getAtk();
        int defense = defender.getStatus().getDefense();

        double stab = (move.getTYPE() == attacker.getType()) ? 1.5 : 1.0;
        double effectiveness = move.getTYPE().getEffectiveness(defender.getType());

        double baseDamage = (((2.0 * attacker.getLevel() / 5 + 2) * power * attack / defense) / 50) + 2;
        int totalDamage = (int) (baseDamage * stab * effectiveness);
        return Math.max(totalDamage, 1);
    }
}
