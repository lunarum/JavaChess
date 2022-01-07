package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

import java.util.ArrayList;

public class Player {
    private final ArrayList<Piece> pieces = new ArrayList<>(16);
    private boolean canCastleKingSide = true;
    private boolean canCastleQueenSide = true;

    public Player() {
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void clearPieces() {
        pieces.clear();
    }

    public boolean isCanCastleKingSide() {
        return canCastleKingSide;
    }

    public void setCanCastleKingSide(boolean canCastleKingSide) {
        this.canCastleKingSide = canCastleKingSide;
    }

    public boolean isCanCastleQueenSide() {
        return canCastleQueenSide;
    }

    public void setCanCastleQueenSide(boolean canCastleQueenSide) {
        this.canCastleQueenSide = canCastleQueenSide;
    }
}
