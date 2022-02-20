package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Knight extends Piece {
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
        return color == Color.BLACK ? -2900 : 2900;
    }
}
