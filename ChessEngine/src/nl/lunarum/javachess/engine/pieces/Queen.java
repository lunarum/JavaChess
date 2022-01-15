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
    public ArrayList<Ply> possiblePlies(Position position) {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossibleStraightRayPlies(plies, position);
        addPossibleDiagonaltRayPlies(plies, position);

        return plies;
    }
}
