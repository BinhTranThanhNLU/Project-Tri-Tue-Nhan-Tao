package model;

import java.util.List;

public class Node {
    private Pokemon activePokemon;
    private Pokemon opponentPokemon;
    private int score; // Giá trị đánh giá trạng thái
    private List<Node> children; // Các trạng thái kế tiếp

    public Node(Pokemon activePokemon, Pokemon opponentPokemon) {
        this.activePokemon = activePokemon;
        this.opponentPokemon = opponentPokemon;
        this.score = 0;
    }

    public Pokemon getActivePokemon() {
        return activePokemon;
    }

    public Pokemon getOpponentPokemon() {
        return opponentPokemon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
