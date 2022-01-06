package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(ChessBoard chessBoard, boolean isBlack, Position position) {
        super(chessBoard, isBlack, position);
    }

    @Override
    public Type type() {
        return Type.KNIGHT;
    }

    @Override
    public ArrayList<Ply> possiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossiblePly(plies, position.upRight(2,-1));
        addPossiblePly(plies, position.upRight(2, 1));
        addPossiblePly(plies, position.upRight(-2,-1));
        addPossiblePly(plies, position.upRight(-2, 1));
        addPossiblePly(plies, position.upRight(-1,2));
        addPossiblePly(plies, position.upRight(1, 2));
        addPossiblePly(plies, position.upRight(-1,-2));
        addPossiblePly(plies, position.upRight(1, -2));

        return plies;
    }
}
