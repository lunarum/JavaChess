package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.*;

public class Player {
    public final Piece.Color color;
    private boolean canCastleKingSide = true;
    private boolean canCastleQueenSide = true;
    public final King king;
    public final Queen queen;
    public final Bishop bishop;
    public final Knight knight;
    public final Rook rook;
    public final Pawn pawn;

    public Player(Piece.Color color) {
        this.color = color;
        king = new King(color);
        queen = new Queen(color);
        bishop = new Bishop(color);
        knight = new Knight(color);
        rook = new Rook(color);
        pawn = new Pawn(color);
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
