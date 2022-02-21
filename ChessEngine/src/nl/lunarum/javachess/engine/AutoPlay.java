package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

public class AutoPlay {
    private record Score(Ply ply, int score) {
    }

    private final ChessBoard board;

    public AutoPlay(ChessBoard chessBoard) {
        board = chessBoard;
    }

    public Ply getBestPly() {
        Score bestScore = getBestPly(5);
        return bestScore.ply;
    }

    private Score getBestPly(int depth) {
        Piece.Color color = board.getCurrentPlayer().color;
        Score bestScore = new Score(null, (color == Piece.Color.BLACK) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
        var plies = board.getPossiblePlies();
        if (plies.size() == 0) return bestScore;
        for(var ply : plies) {
            board.playPly(ply);
            Score score = new Score(ply, (depth > 1) ? getBestPly(depth - 1).score : board.getScore());
            if (color == Piece.Color.BLACK) {
                if (score.score < bestScore.score)
                    bestScore = score;
            } else {
                if (score.score > bestScore.score) {
                    bestScore = score;
                }
            }
            board.retractPly(ply);
        }

        return bestScore;
    }
}
