package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Bishop extends Piece {
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
        return color == Color.BLACK ? -3000 : 3000;
    }
}
