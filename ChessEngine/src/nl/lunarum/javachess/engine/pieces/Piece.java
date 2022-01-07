package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.ChessBoard;
import nl.lunarum.javachess.engine.Player;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;


public abstract class Piece {
    public enum Type {
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
    }

    public final ChessBoard chessBoard;
    public final boolean isBlack;
    protected Position position;

    public Piece(ChessBoard chessBoard, boolean isBlack, Position position) {
        this.chessBoard = chessBoard;
        this.isBlack = isBlack;
        this.position = position;
        chessBoard.addPiece(this);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        if (isBlack)
            return chessBoard.getBlackPlayer();
        return chessBoard.getWhitePlayer();
    }

    public abstract ArrayList<Ply> possiblePlies();
    public abstract Type type();

    /**
     * Add a new Ply if given Position is valid and empty or occupied with a Piece of the opposition.
     * @param plies list to add the new Ply to
     * @param position to Position to possibly move to (null is allowed)
     * @return true if position valid and empty
     */
    protected boolean addPossiblePly(ArrayList<Ply> plies, Position position) {
        if (position == null) // No position?
            return false;

        var piece = chessBoard.onSquare(position);
        if (piece == null) { // Empty square?
            plies.add(new Ply(this, position));
            return true;
        }
        if (piece.isBlack == isBlack) // Position occupied with a piece of the same color?
            return false;
        plies.add(new Ply(this, position, piece));
        return false;
    }

    /**
     * Add all plies when moving in a straight line from the current position.
     * Stop adding until the Position is not valid or when a capture (Piece of the opposition) is found.
     * @param plies list to add the new Plies to
     */
    protected void addPossibleStraightRayPlies(ArrayList<Ply> plies) {
        Position position1 = position;
        do
            position1 = position1.up(1);
        while (addPossiblePly(plies, position1));
        position1 = position;
        do
            position1 = position1.up(-1);
        while (addPossiblePly(plies, position1));
        position1 = position;
        do
            position1 = position1.right(1);
        while (addPossiblePly(plies, position1));
        position1 = position;
        do
            position1 = position1.right(-1);
        while (addPossiblePly(plies, position1));
    }

    /**
     * Add all plies when moving in a diagonal line from the current position.
     * Stop adding until the Position is not valid or when a capture (Piece of the opposition) is found.
     * @param plies list to add the new Plies to
     */
    protected void addPossibleDiagonaltRayPlies(ArrayList<Ply> plies) {
        Position position1 = position;
        do
            position1 = position1.upRight(1, 1);
        while (addPossiblePly(plies, position1));
        position1 = position;
        do
            position1 = position1.upRight(1, -1);
        while (addPossiblePly(plies, position1));
        position1 = position;
        do
            position1 = position1.upRight(-1, 1);
        while (addPossiblePly(plies, position1));
        position1 = position;
        do
            position1 = position1.upRight(-1, -1);
        while (addPossiblePly(plies, position1));
    }

    /**
     * Move this Piece to the new position; current position or validity of the move isn't checked and chessboard isn't changed.
     * @param ply the ply to play out
     */
    public void playPly(Ply ply) {
        position = ply.to;
    }

    /**
     * Move this Piece back to the old position; current position or validity of the move isn't checked and chessboard isn't changed.
     * @param ply the ply to retract
     */
    public void retractPly(Ply ply) {
        position = ply.from;
    }}
