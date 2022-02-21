package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Bishop extends Piece {
    // Values from https://github.com/zserge/carnatus
    private static final int BASE_VALUE = 320;
    private static final int[] PST = {
            261, 242, 238, 244, 297, 213, 283, 270,
            309, 340, 355, 278, 281, 351, 322, 298,
            311, 359, 288, 361, 372, 310, 348, 306,
            345, 337, 340, 354, 346, 345, 335, 330,
            333, 330, 337, 343, 337, 336,   0, 327,
            334, 345, 344, 335, 328, 345, 340, 335,
            339, 340, 331, 326, 327, 326, 340, 336,
            313, 322, 305, 308, 306, 305, 310, 310
    };

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public Type type() {
        return Type.BISHOP;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition) {
        addPossibleDiagonalRayPlies(plies, chessBoard, fromPosition);
    }

    @Override
    public int evaluate(ChessBoard chessBoard, Position position) {
        return color == Color.BLACK ? -PST[position.mirroredPosition] : PST[position.position];
    }
}
