package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.Board;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public abstract class Piece {
    public final Board board;
    public final boolean isBlack;
    protected Position position;

    public Piece(Board board, boolean isBlack, Position position) {
        this.board = board;
        this.isBlack = isBlack;
        this.position = position;
        board.addPiece(this);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract ArrayList<Ply> possiblePlies();
}
