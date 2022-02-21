package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;


public abstract class Piece {
    public enum Type {
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
    }
    public enum Color {
        WHITE, BLACK
    }
    public final static String blackPieceTypes = "kqbnrp";
    public final static String whitePieceTypes = "KQBNRP";

    public final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return Character.toString((color == Color.BLACK ? blackPieceTypes : whitePieceTypes).charAt(type().ordinal()));
    }

    public abstract void addPossiblePlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition);
    public abstract Type type();

    /**
     * Add a new Ply if given Position is valid and empty or occupied with a Piece of the opposition.
     * @param plies list to add the new Ply to
     * @param chessBoard the chessboard this piece is placed now
     * @param fromPosition the position from which the piece comes from
     * @param toPosition the position to possibly move to (null is allowed)
     * @return an added Ply or null if toPosition is invalid or occupied with a piece of the same color
     */
    protected Ply addPossiblePly(ArrayList<Ply> plies, ChessBoard chessBoard, Position fromPosition, Position toPosition) {
        if (toPosition == null) // No toPosition?
            return null;

        var piece = chessBoard.onSquare(toPosition);
        if (piece == null) { // Empty square?
            var ply = new Ply(this, fromPosition, toPosition);
            plies.add(ply);
            return ply;
        }
        if (piece.color == color) // Position occupied with a piece of the same color?
            return null;
        var ply = new Ply(this, fromPosition, piece, toPosition);
        plies.add(ply);
        return ply;
    }

    /**
     * Add all plies when moving in a straight line from the current position.
     * Stop adding until the Position is not valid or when a capture (Piece of the opposition) is found.
     * @param plies list to add the new Plies to
     * @param chessBoard the chessboard this piece is placed now
     * @param position the position from which the piece comes from
     */
    protected void addPossibleStraightRayPlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position position) {
        if (position != null) {
            Ply ply;
            Position position1 = position;
            do
                position1 = position1.up(1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
            position1 = position;
            do
                position1 = position1.up(-1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
            position1 = position;
            do
                position1 = position1.right(1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
            position1 = position;
            do
                position1 = position1.right(-1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
        }
    }

    /**
     * Add all plies when moving in a diagonal line from the current position.
     * Stop adding until the Position is not valid or when a capture (Piece of the opposition) is found.
     * @param plies list to add the new Plies to
     * @param chessBoard the chessboard this piece is placed now
     * @param position the position from which the piece comes from
     */
    protected void addPossibleDiagonalRayPlies(ArrayList<Ply> plies, ChessBoard chessBoard, Position position) {
        if (position != null) {
            Ply ply;
            Position position1 = position;
            do
                position1 = position1.upRight(1, 1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
            position1 = position;
            do
                position1 = position1.upRight(1, -1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
            position1 = position;
            do
                position1 = position1.upRight(-1, 1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
            position1 = position;
            do
                position1 = position1.upRight(-1, -1);
            while (position1 != null && (ply = addPossiblePly(plies, chessBoard, position, position1)) != null && ply.capturedPiece == null);
        }
    }

    public static final int MAX_WHITE_VALUE = 20000;
    public static final int MAX_BLACK_VALUE = -20000;

    /**
     * Return the value of this Piece, given the position it is on.
     * @param chessBoard the chessboard this piece is placed now
     * @param position the position this piece is on now
     * @return positive value if it's a white Piece, negative if black.
     */
    public abstract int evaluate(ChessBoard chessBoard, Position position);
}
