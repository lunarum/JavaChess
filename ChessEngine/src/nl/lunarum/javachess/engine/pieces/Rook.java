package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Rook extends Piece {
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
        return color == Color.BLACK ? -5000 : 5000;
    }
}
