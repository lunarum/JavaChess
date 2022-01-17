package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(ChessBoard chessBoard, boolean isBlack) {
        super(chessBoard, isBlack);
    }

    @Override
    public Type type() {
        return Type.ROOK;
    }

    @Override
    public void addPossiblePlies(ArrayList<Ply> plies, Position fromPosition) {
        addPossibleStraightRayPlies(plies, fromPosition);
    }
}
