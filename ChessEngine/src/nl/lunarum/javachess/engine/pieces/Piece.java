package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;


public abstract class Piece {
    public enum Type {
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
    }
    public final static String blackPieceTypes = "kqbnrp";
    public final static String whitePieceTypes = "KQBNRP";

    public final ChessBoard chessBoard;
    public final boolean isBlack;

    public Piece(ChessBoard chessBoard, boolean isBlack) {
        this.chessBoard = chessBoard;
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return Character.toString((isBlack ? blackPieceTypes : whitePieceTypes).charAt(type().ordinal()));
    }

    public abstract void addPossiblePlies(ArrayList<Ply> plies, Position fromPosition);
    public abstract Type type();

    /**
     * Add a new Ply if given Position is valid and empty or occupied with a Piece of the opposition.
     * @param plies list to add the new Ply to
     * @param fromPosition to position from which the pice comes from
     * @param toPosition the position to possibly move to (null is allowed)
     * @return true if toPosition valid and empty
     */
    protected boolean addPossiblePly(ArrayList<Ply> plies, Position fromPosition, Position toPosition) {
        if (toPosition == null) // No toPosition?
            return false;

        var piece = chessBoard.onSquare(toPosition);
        if (piece == null) { // Empty square?
            plies.add(new Ply(this, fromPosition, toPosition));
            return true;
        }
        if (piece.isBlack == isBlack) // Position occupied with a piece of the same color?
            return false;
        plies.add(new Ply(this, fromPosition, toPosition, piece));
        return false;
    }

    /**
     * Add all plies when moving in a straight line from the current position.
     * Stop adding until the Position is not valid or when a capture (Piece of the opposition) is found.
     * @param plies list to add the new Plies to
     */
    protected void addPossibleStraightRayPlies(ArrayList<Ply> plies, Position position) {
        Position position1 = position;
        do
            position1 = position1.up(1);
        while (addPossiblePly(plies, position, position1));
        position1 = position;
        do
            position1 = position1.up(-1);
        while (addPossiblePly(plies, position, position1));
        position1 = position;
        do
            position1 = position1.right(1);
        while (addPossiblePly(plies, position, position1));
        position1 = position;
        do
            position1 = position1.right(-1);
        while (addPossiblePly(plies, position, position1));
    }

    /**
     * Add all plies when moving in a diagonal line from the current position.
     * Stop adding until the Position is not valid or when a capture (Piece of the opposition) is found.
     * @param plies list to add the new Plies to
     */
    protected void addPossibleDiagonaltRayPlies(ArrayList<Ply> plies, Position position) {
        Position position1 = position;
        do
            position1 = position1.upRight(1, 1);
        while (addPossiblePly(plies, position, position1));
        position1 = position;
        do
            position1 = position1.upRight(1, -1);
        while (addPossiblePly(plies, position, position1));
        position1 = position;
        do
            position1 = position1.upRight(-1, 1);
        while (addPossiblePly(plies, position, position1));
        position1 = position;
        do
            position1 = position1.upRight(-1, -1);
        while (addPossiblePly(plies, position, position1));
    }
}
