package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(ChessBoard chessBoard, boolean isBlack) {
        super(chessBoard, isBlack);
    }

    @Override
    public Type type() {
        return Type.KNIGHT;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, Position fromPosition) {
        addPossiblePly(plies, fromPosition, fromPosition.upRight(2,-1));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(2, 1));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(-2,-1));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(-2, 1));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(-1,2));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(1, 2));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(-1,-2));
        addPossiblePly(plies, fromPosition, fromPosition.upRight(1, -2));
    }
}
