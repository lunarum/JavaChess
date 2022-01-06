package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class King extends Piece {
    public King(ChessBoard chessBoard, boolean isBlack, Position position) {
        super(chessBoard, isBlack, position);
    }

    @Override
    public Type type() {
        return Type.KING;
    }

    @Override
    public ArrayList<Ply> possiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossiblePly(plies, position.up(1));
        addPossiblePly(plies, position.upRight(1, 1));
        addPossiblePly(plies, position.right(1));
        addPossiblePly(plies, position.upRight(1, -1));
        addPossiblePly(plies, position.up(-1));
        addPossiblePly(plies, position.upRight(-1, -1));
        addPossiblePly(plies, position.right(-1));
        addPossiblePly(plies, position.upRight(-1, 1));

        return plies;
    }
}
