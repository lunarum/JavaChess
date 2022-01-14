package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

public class Ply {
    public final Piece piece;
    public final Position from;
    public final Position to;
    public final Piece capturedPiece;
    public final Piece.Type promotedPiece;
    private int move;
    private int previousHalfMoves;

    public Ply(Piece piece, Position to) {
        this.piece = piece;
        this.from = piece.getPosition();
        this.to = to;
        this.capturedPiece = null;
        this.promotedPiece = null;
    }

    public Ply(Piece piece, Position to, Piece capturedPiece) {
        this.piece = piece;
        this.from = piece.getPosition();
        this.to = to;
        this.capturedPiece = capturedPiece;
        this.promotedPiece = null;
    }

    public Ply(Piece piece, Position to, Piece.Type promotedPiece) {
        this.piece = piece;
        this.from = piece.getPosition();
        this.to = to;
        this.capturedPiece = null;
        this.promotedPiece = promotedPiece;
    }

    public Ply(Piece piece, Position to, Piece capturedPiece, Piece.Type promotedPiece) {
        this.piece = piece;
        this.from = piece.getPosition();
        this.to = to;
        this.capturedPiece = capturedPiece;
        this.promotedPiece = promotedPiece;
    }

    public int getMove() {
        return move;
    }

    public int getPreviousHalfMoves() {
        return previousHalfMoves;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void setPreviousHalfMoves(int previousHalfMoves) {
        this.previousHalfMoves = previousHalfMoves;
    }

    public boolean isKingCastling() {
        if (piece.type() == Piece.Type.KING) {
            return (piece.isBlack) ?
                    from.compareTo(Position.E8) == 0 && to.compareTo(Position.G8) == 0 :
                    from.compareTo(Position.E1) == 0 && to.compareTo(Position.G1) == 0;
        }
        return false;
    }

    public boolean isQueenCastling() {
        if (piece.type() == Piece.Type.KING) {
            return (piece.isBlack) ?
                    from.compareTo(Position.E8) == 0 && to.compareTo(Position.C8) == 0 :
                    from.compareTo(Position.E1) == 0 && to.compareTo(Position.C1) == 0;
        }
        return false;
    }
}
