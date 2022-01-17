package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(ChessBoard chessBoard, boolean isBlack) {
        super(chessBoard, isBlack);
    }

    @Override
    public Type type() {
        return Type.QUEEN;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, Position fromPosition) {
        addPossibleStraightRayPlies(plies, fromPosition);
        addPossibleDiagonaltRayPlies(plies, fromPosition);
    }
}
