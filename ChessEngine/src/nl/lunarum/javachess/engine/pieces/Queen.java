package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(ChessBoard chessBoard, boolean isBlack, Position position) {
        super(chessBoard, isBlack, position);
    }

    @Override
    public Type type() {
        return Type.QUEEN;
    }

    @Override
    public ArrayList<Ply> possiblePlies() {
        ArrayList<Ply> plies = new ArrayList<>();

        addPossibleStraightRayPlies(plies);
        addPossibleDiagonaltRayPlies(plies);

        return plies;
    }
}
