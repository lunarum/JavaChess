package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {
    private final Piece[] squares = new Piece[64];
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void addPiece(Piece piece) {
        int index = piece.getPosition().index();
        assert squares[index] == null : "Position is already occupied";
        squares[index] = piece;
        if (piece.isBlack)
            blackPieces.add(piece);
        else
            whitePieces.add(piece);
    }

    public void removePiece(Piece piece) {
        assert squares[piece.getPosition().index()] == piece : "Piece not found";
        removePiece(piece.getPosition());
    }

    public void removePiece(Position position) {
        int index = position.index();
        var piece = squares[index];
        assert piece != null : "Position is already empty";
        squares[index] = null;
        piece.setPosition(null);
    }

    public void clear() {
        Arrays.fill(squares, null);
        whitePieces.clear();
        blackPieces.clear();
    }

    public Piece onSquare(Position position) {
        if (position == null)
            return null;
        return squares[position.index()];
    }

    public void setup() {
        clear();

        // Black
        new Rook(this, true, Position.A8);
        new Knight(this, true, Position.B8);
        new Bishop(this, true, Position.C8);
        new Queen(this, true, Position.D8);
        new King(this, true, Position.E8);
        new Bishop(this, true, Position.F8);
        new Knight(this, true, Position.G8);
        new Rook(this, true, Position.H8);
        new Pawn(this, true, Position.A7);
        new Pawn(this, true, Position.B7);
        new Pawn(this, true, Position.C7);
        new Pawn(this, true, Position.D7);
        new Pawn(this, true, Position.E7);
        new Pawn(this, true, Position.F7);
        new Pawn(this, true, Position.G7);
        new Pawn(this, true, Position.H7);

        // White
        new Pawn(this, false, Position.A2);
        new Pawn(this, false, Position.B2);
        new Pawn(this, false, Position.C2);
        new Pawn(this, false, Position.D2);
        new Pawn(this, false, Position.E2);
        new Pawn(this, false, Position.F2);
        new Pawn(this, false, Position.G2);
        new Pawn(this, false, Position.H2);
        new Rook(this, false, Position.A4);
        new Knight(this, false, Position.B4);
        new Bishop(this, false, Position.C4);
        new Queen(this, false, Position.D4);
        new King(this, false, Position.E4);
        new Bishop(this, false, Position.F1);
        new Knight(this, false, Position.G1);
        new Rook(this, false, Position.H1);
    }
}
