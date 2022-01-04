package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Pawn;
import nl.lunarum.javachess.engine.pieces.Piece;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final Piece[] squares = new Piece[64];
    private final ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private final ArrayList<Piece> whitePieces = new ArrayList<>(16);

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

    public void setup() {
        clear();

        new Pawn(this, true, Position.A7);
        new Pawn(this, true, Position.B7);
        new Pawn(this, true, Position.C7);
        new Pawn(this, true, Position.D7);
        new Pawn(this, true, Position.E7);
        new Pawn(this, true, Position.F7);
        new Pawn(this, true, Position.G8);
        new Pawn(this, true, Position.H7);

        new Pawn(this, false, Position.A2);
        new Pawn(this, false, Position.B2);
        new Pawn(this, false, Position.C2);
        new Pawn(this, false, Position.D2);
        new Pawn(this, false, Position.E2);
        new Pawn(this, false, Position.F2);
        new Pawn(this, false, Position.G2);
        new Pawn(this, false, Position.H2);
    }
}
