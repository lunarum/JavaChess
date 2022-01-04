package nl.lunarum.javachess.engine.pieces;

import nl.lunarum.javachess.engine.Board;
import nl.lunarum.javachess.engine.Ply;
import nl.lunarum.javachess.engine.Position;

import java.util.ArrayList;

public abstract class Piece {
    public final boolean isBlack;
    protected Position position;
    public final Board board;

    public Piece(boolean isBlack, Position position, Board board) {
        this.isBlack = isBlack;
        this.position = position;
        this.board = board;
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
