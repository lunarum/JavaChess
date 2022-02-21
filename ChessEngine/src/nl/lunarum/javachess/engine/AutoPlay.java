package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

public class AutoPlay {
    private final ChessBoard board;

    public AutoPlay(ChessBoard chessBoard) {
        board = chessBoard;
    }

    public Ply getBestPly(int depth) {
        var plies = board.getPossiblePlies();
        if (plies.size() == 0) return null;

        Piece.Color color = board.getCurrentPlayer().color;
        Ply bestPly = null;
        int bestScore = (color == Piece.Color.BLACK) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for(var ply : plies) {
            board.playPly(ply);
            int score = board.getScore();
            if (color == Piece.Color.BLACK) {
                if (score < bestScore) {
                    bestPly = ply;
                    bestScore = score;
                }
            } else {
                if (score > bestScore) {
                    bestPly = ply;
                    bestScore = score;
                }
            }
            board.retractPly(ply);
        }

        return bestPly;
    }
}
