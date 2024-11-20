package model;

public class MiniMax {
    public Move getBestMove(Node root, int depth, boolean isMaximizing) {
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = null;

        for (Node child : root.getChildren()) {
            int score = minimax(child, depth - 1, !isMaximizing);
            if (isMaximizing && score > bestScore || !isMaximizing && score < bestScore) {
                bestScore = score;
                bestMove = child.getActivePokemon().getMoves().get(0); // Lấy chiêu tương ứng
            }
        }
        return bestMove;
    }

    private int minimax(Node node, int depth, boolean isMaximizing) {
        if (depth == 0 || node.getOpponentPokemon().getStatus().getHp() <= 0) {
            return evaluate(node);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                int eval = minimax(child, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Node child : node.getChildren()) {
                int eval = minimax(child, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    // Hàm đánh giá trạng thái
    private int evaluate(Node node) {
        return node.getActivePokemon().getStatus().getHp() - node.getOpponentPokemon().getStatus().getHp();
    }
}
