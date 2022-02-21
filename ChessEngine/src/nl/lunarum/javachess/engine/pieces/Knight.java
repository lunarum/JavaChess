package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Knight extends Piece {
    // Values from https://github.com/zserge/carnatus
    private static final int BASE_VALUE = 280;
    private static final int[] PST = {
            214, 227, 205, 205, 270, 225, 222, 210,
            277, 274, 380, 244, 284, 342, 276, 266,
            290, 347, 281, 354, 353, 307, 342, 278,
            304, 304, 325, 317, 313, 321, 305, 297,
            279, 285, 311, 301, 302, 315, 282,   0,
            262, 290, 293, 302, 298, 295, 291, 266,
            257, 265, 282,   0, 282,   0, 257, 260,
            206, 257, 254, 256, 261, 245, 258, 211,
    };

    public Knight(Color color) {
        super(color);
    }

    @Override
    public Type type() {
        return Type.KNIGHT;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition) {
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(2,-1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(2, 1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(-2,-1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(-2, 1));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(-1,2));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(1, 2));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(-1,-2));
        addPossiblePly(plies, chessBoard, fromPosition, fromPosition.upRight(1, -2));
    }

    @Override
    public int evaluate(ChessBoard chessBoard, Position position) {
        return color == Color.BLACK ? -PST[position.mirroredPosition] : PST[position.position];
    }
}
