package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.*;

import java.util.ArrayList;

public class Player {
    public final boolean isBlack;
    private boolean canCastleKingSide = true;
    private boolean canCastleQueenSide = true;
    public final King king;
    public final Queen queen;
    public final Bishop bishop;
    public final Knight knight;
    public final Rook rook;
    public final Pawn pawn;

    public Player(ChessBoard chessBoard, boolean isBlack) {
        this.isBlack = isBlack;
        king = new King(chessBoard, isBlack);
        queen = new Queen(chessBoard, isBlack);
        bishop = new Bishop(chessBoard, isBlack);
        knight = new Knight(chessBoard, isBlack);
        rook = new Rook(chessBoard, isBlack);
        pawn = new Pawn(chessBoard, isBlack);
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
