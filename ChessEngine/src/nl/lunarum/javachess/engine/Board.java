package nl.lunarum.javachess.engine;

import nl.lunarum.javachess.engine.pieces.Piece;

import java.util.ArrayList;

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
}
