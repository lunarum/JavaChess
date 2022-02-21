package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

public class Ply {
    public enum Annotation {
        None(0),
        KingSideCastling(1),
        QueenSideCastling(2),
        Check(4),
        CheckMate(8);

        private int flag;

        Annotation(int flag) {
            this.flag = flag;
        }

        public boolean isPartOf(int flags) {
            return (flags & flag) != 0;
        }

        public int set(int flags) {
            return flags | flag;
        }

        public int reset(int flags) {
            return flags & (~flag);
        }
    }

    public final Piece piece;
    public final Position from;
    public final Position to;
    public final Piece capturedPiece;
    public final Piece promotedPiece;
    private int move;
    private int previousHalfMoves;
    private int annotations = 0;

    public Ply(Piece piece, Position from, Position to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.capturedPiece = null;
        this.promotedPiece = null;
    }

    public Ply(Piece piece, Position from, Piece capturedPiece, Position to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.capturedPiece = capturedPiece;
        this.promotedPiece = null;
    }

    public Ply(Piece piece, Position from, Position to, Piece promotedPiece) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.capturedPiece = null;
        this.promotedPiece = promotedPiece;
    }

    public Ply(Piece piece, Position from, Position to, Piece capturedPiece, Piece promotedPiece) {
        this.piece = piece;
        this.from = from;
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
        return Annotation.KingSideCastling.isPartOf(annotations);
    }

    public boolean isQueenCastling() {
        return Annotation.QueenSideCastling.isPartOf(annotations);
    }

    public void setAnnotation(Annotation annotation) {
        annotations = annotation.set(annotations);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isKingCastling())
            builder.append("O-O");
        else if (isQueenCastling())
            builder.append("O-O-O");
        else {
            if (piece.type() != Piece.Type.PAWN)
                builder.append(Piece.whitePieceTypes.charAt(piece.type().ordinal()));
            builder.append(from);
            builder.append(capturedPiece == null ? '-' : 'x');
            builder.append(to);
        }
        if (Annotation.CheckMate.isPartOf(annotations))
            builder.append('#');
        else if (Annotation.Check.isPartOf(annotations))
            builder.append('+');
        return builder.toString();
    }
}
