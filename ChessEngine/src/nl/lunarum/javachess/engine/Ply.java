package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

public record Ply (Piece piece, Position from, Position to, Piece capturedPiece, Piece.Type promotedPiece) {

    public Ply(Piece piece, Position to) {
        this(piece, piece.getPosition(), to, null, null);
    }

    public Ply(Piece piece, Position to, Piece capturedPiece) {
        this(piece, piece.getPosition(), to, capturedPiece, null);
    }

    public Ply(Piece piece, Position to, Piece.Type promotedPiece) {
        this(piece, piece.getPosition(), to, null, promotedPiece);
    }

    public Ply(Piece piece, Position to, Piece capturedPiece, Piece.Type promotedPiece) {
        this(piece, piece.getPosition(), to, capturedPiece, promotedPiece);
    }
}
