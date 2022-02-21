package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Rook extends Piece {
    // Values from https://github.com/zserge/carnatus
    private static final int BASE_VALUE = 479;
    private static final int[] PST = {
            514, 508, 512, 483, 516, 512, 535, 529,
            534, 508, 535, 546, 534, 541, 513, 539,
            498, 514, 507, 512, 524, 506, 504, 494,
              0, 484, 495, 492, 497, 475, 470, 473,
            451, 444, 463, 458, 466, 450, 433, 449,
            437, 451, 437, 454, 454, 444, 453, 433,
            426, 441, 448, 453, 450, 436, 435, 426,
            449, 455, 461, 484, 477, 461, 448, 447,
    };

    public Rook(Color color) {
        super(color);
    }

    @Override
    public Type type() {
        return Type.ROOK;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition) {
        addPossibleStraightRayPlies(plies, chessBoard, fromPosition);
    }

    @Override
    public int evaluate(ChessBoard chessBoard, Position position) {
        return color == Color.BLACK ? -PST[position.mirroredPosition] : PST[position.position];
    }
}
