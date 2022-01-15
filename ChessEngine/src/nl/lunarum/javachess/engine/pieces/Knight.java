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
    public ArrayList<Ply> possiblePlies(Position position) {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossiblePly(plies, position, position.upRight(2,-1));
        addPossiblePly(plies, position, position.upRight(2, 1));
        addPossiblePly(plies, position, position.upRight(-2,-1));
        addPossiblePly(plies, position, position.upRight(-2, 1));
        addPossiblePly(plies, position, position.upRight(-1,2));
        addPossiblePly(plies, position, position.upRight(1, 2));
        addPossiblePly(plies, position, position.upRight(-1,-2));
        addPossiblePly(plies, position, position.upRight(1, -2));

        return plies;
    }
}
